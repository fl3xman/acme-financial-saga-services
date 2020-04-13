package org.acme.commons.outbox.domain

import com.fasterxml.jackson.databind.ObjectMapper
import org.acme.commons.domain.AggregateIdentity
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
    var id: UUID? = null,

    @Column(name = "aggregate_id")
    val aggregateId: UUID,
    val topic: String,
    val payload: String
) {
    constructor(topic: String, payload: AggregateIdentity<UUID>, objectMapper: ObjectMapper) : this(
        topic = topic, aggregateId = payload.aggregateId, payload = objectMapper.writeValueAsString(payload)
    )
}