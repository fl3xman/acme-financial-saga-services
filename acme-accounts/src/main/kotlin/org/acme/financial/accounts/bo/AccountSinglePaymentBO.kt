package org.acme.financial.accounts.bo

import org.acme.commons.money.MonetaryOperationType
import org.acme.commons.money.beneficiary.Beneficiary
import org.acme.financial.accounts.command.AccountSinglePaymentOutboxCommand
import org.acme.financial.accounts.domain.*
import org.javamoney.moneta.Money
import java.time.LocalDateTime
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class AccountSinglePaymentBO(
    val id: UUID,
    val accountId: UUID,
    val transaction: Money,
    val beneficiary: Beneficiary,
    val status: AccountOperationStatus,
    val createdAt: LocalDateTime
) {
    fun process(
        topic: String,
        payee: Account?,
        payer: Account?,
        payerBalance: Money?,
        existing: Long
    ): AccountSinglePaymentOutboxCommand {
        return when(true) {
            existing > 0 ->                 AccountSinglePaymentOutboxCommand.InvalidOperation(topic, this)
            payee == null ->                AccountSinglePaymentOutboxCommand.InvalidAccount(topic, this)
            payer == null ->                AccountSinglePaymentOutboxCommand.InvalidAccount(topic, this)
            payee.id == accountId ->        AccountSinglePaymentOutboxCommand.InvalidBeneficiary(topic, this)
            payerBalance == null ->         AccountSinglePaymentOutboxCommand.InvalidBalance(topic, this)
            payerBalance < transaction ->   AccountSinglePaymentOutboxCommand.InsufficientBalance(topic, this)
            else -> {
                deposit(payee, payer)
                withdraw(payer)
                AccountSinglePaymentOutboxCommand.Approved(topic, this)
            }
        }
    }

    private fun deposit(payee: Account, payer: Account) {
        payee.operations.add(
            AccountOperation(
                transaction = AccountTransaction(id, AccountTransactionType.SINGLE_PAYMENT, transaction),
                beneficiary = payer.beneficiary,
                type = MonetaryOperationType.DEPOSIT
            ).also {
                it.account = payee
            }
        )
    }

    private fun withdraw(payer: Account) {
        payer.operations.add(
            AccountOperation(
                transaction = AccountTransaction(id, AccountTransactionType.SINGLE_PAYMENT, transaction.negate()),
                beneficiary = beneficiary,
                type = MonetaryOperationType.WITHDRAW
            ).also {
                it.account = payer
            }
        )
    }
}