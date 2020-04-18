package org.acme.financial.accounts.domain

import com.fasterxml.jackson.annotation.JsonValue

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
enum class AccountOperationStatus(@JsonValue val value: String) {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    fun isApproved(): Boolean = this == APPROVED
}