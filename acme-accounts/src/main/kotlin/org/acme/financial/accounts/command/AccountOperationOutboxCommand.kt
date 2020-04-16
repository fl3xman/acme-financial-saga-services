package org.acme.financial.accounts.command

import org.acme.commons.domain.AggregateIdentity
import org.acme.commons.outbox.command.OutboxCommand
import org.acme.financial.accounts.bo.AccountOperationExchangeBO
import org.acme.financial.accounts.domain.AccountOperationReason
import org.acme.financial.accounts.domain.AccountOperationStatus
import org.acme.financial.accounts.dto.AccountOperationExchangeDTO
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

sealed class AccountOperationOutboxCommand(
    override val topic: String,
    override val payload: AccountOperationExchangeDTO
): OutboxCommand<AccountOperationExchangeDTO> {

    data class Approved(override val topic: String, val exchange: AccountOperationExchangeBO): AccountOperationOutboxCommand(
        topic, AccountOperationExchangeDTO(exchange, AccountOperationStatus.APPROVED)
    )

    data class InsufficientBalance(override val topic: String, val exchange: AccountOperationExchangeBO): AccountOperationOutboxCommand(
        topic, AccountOperationExchangeDTO(exchange, AccountOperationStatus.REJECTED, AccountOperationReason.INSUFFICIENT_BALANCE)
    )

    data class InvalidBalance(override val topic: String, val exchange: AccountOperationExchangeBO): AccountOperationOutboxCommand(
        topic, AccountOperationExchangeDTO(exchange, AccountOperationStatus.REJECTED, AccountOperationReason.INVALID_BALANCE)
    )

    data class InvalidBeneficiary(override val topic: String, val exchange: AccountOperationExchangeBO): AccountOperationOutboxCommand(
        topic, AccountOperationExchangeDTO(exchange, AccountOperationStatus.REJECTED, AccountOperationReason.INVALID_BENEFICIARY)
    )

    data class InvalidAccount(override val topic: String, val exchange: AccountOperationExchangeBO): AccountOperationOutboxCommand(
        topic, AccountOperationExchangeDTO(exchange, AccountOperationStatus.REJECTED, AccountOperationReason.INVALID_ACCOUNT)
    )
}