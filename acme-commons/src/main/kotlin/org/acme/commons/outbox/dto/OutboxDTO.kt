package org.acme.commons.outbox.dto

import org.acme.commons.domain.AggregateIdentity
import org.acme.commons.domain.Identity
import org.acme.commons.message.MessagePayloadAware
import org.acme.commons.message.MessageTopicAware
import org.acme.commons.outbox.domain.Outbox
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class OutboxDTO(
    override val id: UUID,
    override val aggregateId: UUID,
    override val topic: String,
    override val payload: String
) : Identity<UUID>, AggregateIdentity<UUID>, MessageTopicAware, MessagePayloadAware {
    constructor(model: Outbox) : this(
        // TODO: check if force unwrap can be here
        model.id!!, model.aggregateId, model.topic, model.payload
    )
}