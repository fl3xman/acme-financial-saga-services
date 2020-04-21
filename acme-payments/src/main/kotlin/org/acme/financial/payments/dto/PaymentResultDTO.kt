package org.acme.financial.payments.dto

import org.acme.financial.payments.avro.SinglePaymentResultTransmit
import org.acme.financial.payments.domain.PaymentStatus
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class PaymentResultDTO(
    val id: UUID,
    val status: PaymentStatus,
    val reason: String? = null
) {
    constructor(transmit: SinglePaymentResultTransmit): this(
        UUID.fromString(transmit.getId()),
        PaymentStatus.valueOf(transmit.getReason()),
        transmit.getReason()
    )
}