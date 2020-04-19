package org.acme.financial.accounts.domain

import com.fasterxml.jackson.annotation.JsonValue

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
enum class AccountOperationStatus(@JsonValue val value: String) {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED");

    fun isApproved(): Boolean = this == APPROVED
}