package org.acme.financial.accounts.domain

import com.fasterxml.jackson.annotation.JsonValue

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

enum class AccountOperationReason(@JsonValue val value: String) {
    INSUFFICIENT_BALANCE("${AccountOperationReason.ROOT}.InsufficientBalance"),
    INVALID_BALANCE("${AccountOperationReason.ROOT}.InvalidBalance"),
    INVALID_BENEFICIARY("${AccountOperationReason.ROOT}.InvalidBeneficiary"),
    INVALID_ACCOUNT("${AccountOperationReason.ROOT}. InvalidAccount"),
    INVALID_OPERATION("${AccountOperationReason.ROOT}. InvalidOperation");

    companion object {
        private const val ROOT = "org.acme.financial.accounts.reason"
    }

}

