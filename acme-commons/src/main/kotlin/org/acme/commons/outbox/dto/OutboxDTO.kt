package org.acme.commons.outbox.dto

import org.acme.commons.outbox.domain.Outbox
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class OutboxDTO(
    val id: UUID,
    val aggregateId: UUID,
    val topic: String,
    val event: String,
    val payload: String
) {
    constructor(model: Outbox): this(
        model.id, model.aggregateId, model.topic, model.event, model.payload
    )
}