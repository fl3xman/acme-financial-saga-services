package org.acme.financial.accounts.bo

import org.acme.commons.money.MonetaryOperationType
import org.acme.commons.money.beneficiary.Beneficiary
import org.acme.financial.accounts.command.AccountOperationOutboxCommand
import org.acme.financial.accounts.domain.Account
import org.acme.financial.accounts.domain.AccountOperation
import org.acme.financial.accounts.domain.AccountOperationStatus
import org.javamoney.moneta.Money
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class AccountOperationExchangeBO(
    val id: UUID,
    val accountId: UUID,
    val transaction: Money,
    val beneficiary: Beneficiary,
    val status: AccountOperationStatus,
    val createdAt: LocalDateTime
) {
    fun process(topic: String, payeeAccount: Account?, payerAccount: Account?, payerBalance: Money?): AccountOperationOutboxCommand {
        return payerAccount?.let { payer ->
            payeeAccount?.let { payee ->
                if (payee.id == accountId) AccountOperationOutboxCommand.InvalidBeneficiary(topic, this)
                else {
                    payerBalance?.let { balance ->
                        if (balance < transaction) AccountOperationOutboxCommand.InsufficientBalance(topic, this)
                        else {
                            deposit(payee, payer)
                            withdraw(payer)

                            AccountOperationOutboxCommand.Approved(topic, this)
                        }
                    } ?: AccountOperationOutboxCommand.InvalidBalance(topic, this)
                }
            } ?: AccountOperationOutboxCommand.InvalidBeneficiary(topic, this)
        } ?: AccountOperationOutboxCommand.InvalidAccount(topic, this)
    }

    private fun deposit(payee: Account, payer: Account) {
        payee.operations.add(
            AccountOperation(
                transaction = transaction,
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
                transaction = transaction.negate(),
                beneficiary = beneficiary,
                type = MonetaryOperationType.WITHDRAW
            ).also {
                it.account = payer
            }
        )
    }
}