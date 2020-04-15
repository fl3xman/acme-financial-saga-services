package org.acme.financial.payments.dto

import org.acme.commons.money.beneficiary.Beneficiary
import org.acme.financial.payments.domain.Payment
import org.acme.financial.payments.domain.PaymentStatus
import java.math.BigDecimal
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class PaymentDTO(
    val id: UUID,
    val amount: BigDecimal,
    val currency: String,
    val status: PaymentStatus,
    val beneficiary: Beneficiary
) {
    constructor(model: Payment) : this(
        model.id!!,
        model.transaction.numberStripped,
        model.transaction.currency.currencyCode,
        model.status,
        model.beneficiary
    )
}