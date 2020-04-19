package org.acme.financial.payments.domain

import com.fasterxml.jackson.annotation.JsonValue

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
enum class PaymentStatus(@JsonValue val value: String) {
    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED")
}