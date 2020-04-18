package org.acme.financial.accounts.service

import org.acme.commons.logging.provideLogger
import org.acme.commons.outbox.service.OutboxService
import org.acme.commons.reactor.mapUnit
import org.acme.financial.accounts.dto.AccountOperationDTO
import org.acme.financial.accounts.bo.AccountSinglePaymentBO
import org.acme.financial.accounts.domain.AccountTransactionType
import org.acme.financial.accounts.dto.AccountBalanceDTO
import org.acme.financial.accounts.exception.AccountOperationProcessingException
import org.acme.financial.accounts.repository.AccountOperationRepository
import org.acme.financial.accounts.repository.AccountRepository
import org.javamoney.moneta.Money
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Service
class AccountOperationServiceImp(
    @Autowired private val accountRepository: AccountRepository,
    @Autowired private val accountOperationRepository: AccountOperationRepository,
    @Autowired private val outboxService: OutboxService,
    @Autowired private val transactionTemplate: TransactionTemplate,
    @Value("\${acme.account.topics.single-payment-completed}") private val topic: String
): AccountOperationService {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    override fun getAccountBalances(accountId: UUID): Flux<AccountBalanceDTO> = Flux.defer {
        accountOperationRepository.getAllBalancesByAccountId(accountId).map { AccountBalanceDTO(it) }.toFlux()
    }

    override fun getAccountHistory(accountId: UUID): Flux<AccountOperationDTO> = Flux.defer {
        accountOperationRepository.findAllByAccountId(accountId).map { AccountOperationDTO(it) }.toFlux()
    }

    override fun processAccountSinglePayment(payment: AccountSinglePaymentBO): Mono<Unit> = Mono.defer {
        transactionTemplate.execute {

            val payee = accountRepository.findOneByBeneficiary(payment.beneficiary)
            val payer = accountRepository.findByIdOrNull(payment.accountId)
            val existing = accountOperationRepository.countByTransactionIdAndTransactionType(
                payment.id, AccountTransactionType.SINGLE_PAYMENT
            )
            val payerBalance = accountOperationRepository.getBalanceByAccountIdAndCurrency(
                payment.accountId, payment.transaction.currency.currencyCode
            )?.let { balance -> Money.of(balance, payment.transaction.currency) }

            payment.process(topic, payee, payer, payerBalance, existing).also {
                if (it.payload.status.isApproved()) {
                    accountRepository.saveAll(listOf(payee, payer))
                }
                outboxService.append(it)
            }

        }?.toMono() ?: Mono.error(AccountOperationProcessingException("Account operation failed for payment=$payment"))
    }.mapUnit()
}