package org.acme.commons.kafka.service

import org.acme.commons.domain.AggregateIdentity
import org.acme.commons.domain.Identity
import org.acme.commons.logging.provideLogger
import org.acme.commons.message.MessagePayloadAware
import org.acme.commons.message.MessageTopicAware
import org.acme.commons.message.service.MessageSenderService
import org.acme.commons.reactor.mapUnit
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
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

class KafkaMessageSenderService(
    @Autowired private val kafkaTemplate: KafkaTemplate<String, String>,
    @Autowired private val kafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, String>
) : MessageSenderService {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    // Blocking bulk

    override fun <T> bulkSend(
        payloads: List<T>, complete: (Result<UUID>) -> Unit
    ) where T : Identity<UUID>, T : AggregateIdentity<UUID>, T : MessageTopicAware, T : MessagePayloadAware {
        payloads.forEach {
            kafkaTemplate
                .send(ProducerRecord(it.topic, it.aggregateId.toString(), it.payload))
                .addCallback({ _ ->
                    complete(Result.success(it.id))
                }, { exception ->
                    logger.error("Message failed to send with error=$exception")
                    complete(Result.failure(exception))
                })
        }
    }

    // Non-blocking bulk

    override fun <T> bulkSend(
        payloads: List<T>
    ): Flux<Result<UUID>> where T : Identity<UUID>, T : AggregateIdentity<UUID>, T : MessageTopicAware, T : MessagePayloadAware {
        return kafkaProducerTemplate.send(Flux.fromIterable(payloads.map {
            SenderRecord.create(
                ProducerRecord(it.topic, it.aggregateId.toString(), it.payload),
                it.id
            )
        })).map {
            if (it.exception() == null) Result.success(it.correlationMetadata())
            else Result.failure(it.exception().also { ex ->
                logger.error("Message failed to send with error=$ex")
            })
        }
    }

    // Blocking raw

    override fun send(topic: String, key: String, payload: String, complete: (Result<Unit>) -> Unit) {
        kafkaTemplate.send(topic, key, payload).addCallback({ _ ->
                complete(Result.success(Unit))
            }, { exception ->
                logger.error("Message failed to send with error=$exception")
                complete(Result.failure(exception))
            })
    }

    // Non-blocking raw

    override fun send(topic: String, key: String, payload: String): Mono<Unit> = Mono.defer {
        kafkaProducerTemplate.send(topic, key, payload).mapUnit()
    }
}