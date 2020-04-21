package org.acme.commons.message.service.schema

import org.acme.commons.domain.AggregateIdentity
import org.acme.commons.domain.Identity
import org.acme.commons.message.MessageTopicAware
import org.acme.commons.message.schema.SchemaPayloadAware
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface SchemaSenderService<Schema> {

    fun <T> send(
        payload: T
    ): Mono<Result<UUID>> where T : Identity<UUID>,
                                T : AggregateIdentity<UUID>,
                                T : MessageTopicAware,
                                T : SchemaPayloadAware<Schema> = bulkSend(listOf(payload)).next()

    fun <T> bulkSend(
        payloads: List<T>
    ): Flux<Result<UUID>> where T : Identity<UUID>,
                                T : AggregateIdentity<UUID>,
                                T : MessageTopicAware,
                                T : SchemaPayloadAware<Schema>

    fun send(topic: String, key: String, schema: Schema): Mono<Unit>
}