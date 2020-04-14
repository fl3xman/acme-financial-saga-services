package org.acme.commons.kafka.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.acme.commons.message.service.MessageReceiverService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import reactor.core.publisher.Flux
import reactor.kafka.receiver.ReceiverOptions

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

class KafkaMessageReceiverService(
    @Autowired private val kafkaProperties: KafkaProperties,
    @Autowired private val objectMapper: ObjectMapper
) : MessageReceiverService {

    private val topics = mutableMapOf<String, ReactiveKafkaConsumerTemplate<String, String>>()

    override fun <T> on(topic: String, mappingClass: Class<T>, acknowledged: (T) -> Boolean): Flux<T> = Flux.defer {
        templateBy(topic)
            .receive()
            .map { Pair(objectMapper.readValue(it.value(), mappingClass), it.receiverOffset()) }
            .doOnNext {
                if (acknowledged(it.first)) {
                    it.second.acknowledge()
                }
            }
            .map { it.first }
    }

    private fun templateBy(topic: String): ReactiveKafkaConsumerTemplate<String, String> {
        return topics.getOrPut(topic) {
            ReactiveKafkaConsumerTemplate(
                ReceiverOptions.create<String, String>(kafkaProperties.buildConsumerProperties()).also {
                    it.subscription(listOf(topic))
                }
            )
        }

    }
}