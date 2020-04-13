package org.acme.financial.payments.controller

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

object PaymentRoute {
    const val ROOT = "/payments"
    const val TRANSFER_IBAN = "/payments/iban"
    const val IDENTITY = "/{id}"
}