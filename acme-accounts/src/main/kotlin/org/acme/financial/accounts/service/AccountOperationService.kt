package org.acme.financial.accounts.service

import org.acme.financial.accounts.dto.AccountOperationDTO
import org.acme.financial.accounts.bo.AccountSinglePaymentBO
import org.acme.financial.accounts.dto.AccountBalanceDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface AccountOperationService {

    fun getAccountBalances(accountId: UUID): Flux<AccountBalanceDTO>
    fun getAccountHistory(accountId: UUID): Flux<AccountOperationDTO>

    fun processAccountSinglePayment(payment: AccountSinglePaymentBO): Mono<Unit>
}