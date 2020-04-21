package org.acme.commons.kafka.service.schema

import org.acme.commons.message.MessageDeadLetter
import org.acme.commons.message.avro.DeadLetterTransmit
import org.acme.commons.message.service.schema.DeadLetterMessageSenderService
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.core.publisher.Mono

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
class KafkaDeadLetterMessageSenderService(
    @Autowired deadLetterProducerTemplate: ReactiveKafkaProducerTemplate<String, DeadLetterTransmit>
): KafkaSchemaMessageSenderService<DeadLetterTransmit>(deadLetterProducerTemplate), DeadLetterMessageSenderService {

    override fun send(
        topic: String,
        key: String,
        deadLetter: MessageDeadLetter
    ): Mono<Unit> = send(
        topic,
        key,
        DeadLetterTransmit.newBuilder()
            .setException(deadLetter.exception)
            .setPayload(deadLetter.payload)
            .build()
    )
}