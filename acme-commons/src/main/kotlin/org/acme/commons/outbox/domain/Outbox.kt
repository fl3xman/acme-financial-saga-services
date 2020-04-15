package org.acme.commons.outbox.domain

import com.fasterxml.jackson.databind.ObjectMapper
import org.acme.commons.domain.AggregateIdentity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Entity
@Table(name = "outbox")
@EntityListeners(AuditingEntityListener::class)
data class Outbox(
    @Id
    @GeneratedValue(generator = "UUID")
    var id: UUID? = null,

    @Column(name = "aggregate_id")
    val aggregateId: UUID,
    val topic: String,
    val payload: String,

    @CreatedDate
    @Column(name = "created_at")
    var createdAt: LocalDateTime? = null
) {
    constructor(topic: String, payload: AggregateIdentity<UUID>, objectMapper: ObjectMapper) : this(
        topic = topic, aggregateId = payload.aggregateId, payload = objectMapper.writeValueAsString(payload)
    )
}