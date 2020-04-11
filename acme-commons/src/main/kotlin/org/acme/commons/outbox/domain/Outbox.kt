package org.acme.commons.outbox.domain

import org.acme.commons.outbox.event.OutboxEvent
import org.springframework.data.annotation.Id
import java.util.*
import javax.persistence.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Entity
@Table(name = "outbox")
data class Outbox(
    @Id
    @GeneratedValue(generator = "UUID")
    val id: UUID,

    @Column(name = "aggregate_id")
    val aggregateId: UUID,
    val topic: String,
    val event: String,
    val payload: String
) {
    constructor(event: OutboxEvent): this(
        UUID.randomUUID(), event.aggregateId, event.topic, event.event, event.payload
    )
}