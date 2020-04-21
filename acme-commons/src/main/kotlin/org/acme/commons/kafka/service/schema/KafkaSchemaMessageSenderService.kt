package org.acme.commons.kafka.service.schema

import org.acme.commons.domain.AggregateIdentity
import org.acme.commons.domain.Identity
import org.acme.commons.logging.provideLogger
import org.acme.commons.message.MessageTopicAware
import org.acme.commons.message.schema.SchemaMessagePayloadAware
import org.acme.commons.message.service.schema.SchemaMessageSenderService
import org.acme.commons.reactor.mapUnit
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kafka.sender.SenderRecord
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
abstract class KafkaSchemaMessageSenderService<Schema>(
    private val schemaProducerTemplate: ReactiveKafkaProducerTemplate<String, Schema>
): SchemaMessageSenderService<Schema> {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    override fun <T> bulkSend(
        payloads: List<T>
    ): Flux<Result<UUID>> where T : Identity<UUID>, T : AggregateIdentity<UUID>, T : MessageTopicAware, T : SchemaMessagePayloadAware<Schema> {
        return schemaProducerTemplate.send(Flux.fromIterable(payloads.map {
            SenderRecord.create(
                ProducerRecord(it.topic, it.aggregateId.toString(), it.schemaPayload),
                it.id
            )
        })).map {
            if (it.exception() == null) Result.success(it.correlationMetadata())
            else Result.failure(it.exception().also { ex ->
                logger.error("Message failed to send with error=$ex")
            })
        }
    }

    override fun send(topic: String, key: String, schema: Schema): Mono<Unit> {
        return schemaProducerTemplate.send(topic, key, schema).mapUnit()
    }
}