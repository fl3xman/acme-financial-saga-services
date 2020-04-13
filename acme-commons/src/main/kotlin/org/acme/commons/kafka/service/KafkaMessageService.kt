package org.acme.commons.kafka.service

import org.acme.commons.domain.AggregateIdentity
import org.acme.commons.domain.Identity
import org.acme.commons.logging.provideLogger
import org.acme.commons.message.MessagePayloadAware
import org.acme.commons.message.MessageTopicAware
import org.acme.commons.message.service.MessageService
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.core.publisher.Flux
import reactor.kafka.sender.SenderRecord
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

class KafkaMessageService(
    @Autowired private val kafkaTemplate: KafkaTemplate<String, String>,
    @Autowired private val kafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, String>
) : MessageService {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

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

    override fun <T> bulkSend(
        payloads: List<T>
    ): Flux<Result<UUID>> where T : Identity<UUID>, T : AggregateIdentity<UUID>, T : MessageTopicAware, T : MessagePayloadAware {
        return kafkaProducerTemplate.send<UUID> {
            payloads.forEach { data ->
                it.onNext(
                    SenderRecord.create(
                        ProducerRecord(data.topic, data.aggregateId.toString(), data.payload),
                        data.id
                    )
                )
            }
            it.onComplete()
        }.map {
            if (it.exception() == null) Result.success(it.correlationMetadata())
            else Result.failure(it.exception().also { ex ->
                logger.error("Message failed to send with error=$ex")
            })
        }
    }
}