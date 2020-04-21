package org.acme.commons.outbox.dto.schema

import org.acme.commons.domain.AggregateIdentity
import org.acme.commons.domain.Identity
import org.acme.commons.message.MessagePayloadAware
import org.acme.commons.message.MessageTopicAware
import org.acme.commons.message.schema.SchemaPayloadAware
import org.acme.commons.outbox.domain.Outbox
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */


data class OutboxSchemaDTO<Schema>(
    override val id: UUID,
    override val aggregateId: UUID,
    override val topic: String,
    override val schemaPayload: Schema
) : Identity<UUID>, AggregateIdentity<UUID>, MessageTopicAware, SchemaPayloadAware<Schema> {
    constructor(model: Outbox, convert: (payload: String) -> Schema) : this(
        // TODO: check if force unwrap can be here
        model.id!!, model.aggregateId, model.topic, convert(model.payload)
    )
}