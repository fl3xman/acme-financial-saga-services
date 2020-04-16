package org.acme.financial.accounts.domain

import com.fasterxml.jackson.annotation.JsonValue
import org.acme.financial.accounts.bo.AccountOperationExchangeBO
import org.acme.financial.accounts.command.AccountOperationOutboxCommand
import org.acme.financial.accounts.dto.AccountOperationExchangeDTO

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

enum class AccountOperationReason(@JsonValue val value: String) {
    INSUFFICIENT_BALANCE("${AccountOperationReason.ROOT}.InsufficientBalance"),
    INVALID_BALANCE("${AccountOperationReason.ROOT}.InvalidBalance"),
    INVALID_BENEFICIARY("${AccountOperationReason.ROOT}.InvalidBeneficiary"),
    INVALID_ACCOUNT("${AccountOperationReason.ROOT}. InvalidAccount");

    companion object {
        private const val ROOT = "org.acme.financial.accounts.reason"
    }

}

