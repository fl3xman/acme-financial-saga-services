package org.acme.commons.outbox.event

import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class OutboxEvent(
    val aggregateId: UUID,
    val topic: String,
    val event: String,
    val payload: String
)