package org.acme.financial.accounts.bo

import org.acme.commons.money.MonetaryOperationType
import org.acme.commons.money.beneficiary.Beneficiary
import org.acme.financial.accounts.command.AccountSinglePaymentOutboxCommand
import org.acme.financial.accounts.domain.Account
import org.acme.financial.accounts.domain.AccountOperation
import org.acme.financial.accounts.domain.AccountOperationStatus
import org.acme.financial.accounts.domain.AccountMoney
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
    fun process(topic: String, payee: Account?, payer: Account?, payerBalance: Money?): AccountSinglePaymentOutboxCommand {
        return when(true) {
            payee == null ->                AccountSinglePaymentOutboxCommand.InvalidAccount(topic, this)
            payer == null ->                AccountSinglePaymentOutboxCommand.InvalidAccount(topic, this)
            payee?.id == accountId ->       AccountSinglePaymentOutboxCommand.InvalidBeneficiary(topic, this)
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
                transaction = AccountMoney(transaction),
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
                transaction = AccountMoney(transaction.negate()),
                beneficiary = beneficiary,
                type = MonetaryOperationType.WITHDRAW
            ).also {
                it.account = payer
            }
        )
    }
}