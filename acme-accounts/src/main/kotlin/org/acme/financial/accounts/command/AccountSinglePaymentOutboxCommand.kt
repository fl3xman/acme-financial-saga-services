package org.acme.financial.accounts.command

import org.acme.commons.outbox.command.OutboxCommand
import org.acme.financial.accounts.bo.AccountSinglePaymentBO
import org.acme.financial.accounts.domain.AccountOperationReason
import org.acme.financial.accounts.domain.AccountOperationStatus
import org.acme.financial.accounts.dto.AccountOperationResultDTO

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

sealed class AccountSinglePaymentOutboxCommand(
    override val topic: String,
    override val payload: AccountOperationResultDTO
): OutboxCommand<AccountOperationResultDTO> {

    data class Approved(override val topic: String, val payment: AccountSinglePaymentBO): AccountSinglePaymentOutboxCommand(
        topic, AccountOperationResultDTO(payment, AccountOperationStatus.APPROVED)
    )

    data class InvalidOperation(override val topic: String, val payment: AccountSinglePaymentBO): AccountSinglePaymentOutboxCommand(
        topic, AccountOperationResultDTO(payment, AccountOperationStatus.REJECTED, AccountOperationReason.INSUFFICIENT_BALANCE)
    )

    data class InsufficientBalance(override val topic: String, val payment: AccountSinglePaymentBO): AccountSinglePaymentOutboxCommand(
        topic, AccountOperationResultDTO(payment, AccountOperationStatus.REJECTED, AccountOperationReason.INSUFFICIENT_BALANCE)
    )

    data class InvalidBalance(override val topic: String, val payment: AccountSinglePaymentBO): AccountSinglePaymentOutboxCommand(
        topic, AccountOperationResultDTO(payment, AccountOperationStatus.REJECTED, AccountOperationReason.INVALID_BALANCE)
    )

    data class InvalidBeneficiary(override val topic: String, val payment: AccountSinglePaymentBO): AccountSinglePaymentOutboxCommand(
        topic, AccountOperationResultDTO(payment, AccountOperationStatus.REJECTED, AccountOperationReason.INVALID_BENEFICIARY)
    )

    data class InvalidAccount(override val topic: String, val payment: AccountSinglePaymentBO): AccountSinglePaymentOutboxCommand(
        topic, AccountOperationResultDTO(payment, AccountOperationStatus.REJECTED, AccountOperationReason.INVALID_ACCOUNT)
    )
}