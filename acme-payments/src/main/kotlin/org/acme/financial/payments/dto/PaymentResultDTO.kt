package org.acme.financial.payments.dto

import org.acme.financial.payments.avro.PaymentResultSchema
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
    constructor(schema: PaymentResultSchema): this(
        UUID.fromString(schema.getId()),
        PaymentStatus.valueOf(schema.getStatus().name),
        schema.getReason()
    )
}