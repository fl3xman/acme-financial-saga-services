package org.acme.commons.kafka.service.schema

import org.acme.commons.message.DeadLetter
import org.acme.commons.message.avro.DeadLetterSchema
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.core.publisher.Mono

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
class DeadLetterSenderService(
    @Autowired schemaTemplate: ReactiveKafkaProducerTemplate<String, DeadLetterSchema>
): KafkaSchemaSenderService<DeadLetterSchema>(schemaTemplate) {

    fun send(
        topic: String,
        key: String,
        letter: DeadLetter
    ): Mono<Unit> = send(
        topic, key, DeadLetterSchema.newBuilder().apply {
            exception = letter.exception
            payload = letter.payload
        }.build()
    )
}