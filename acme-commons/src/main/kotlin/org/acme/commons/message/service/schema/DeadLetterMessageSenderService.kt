package org.acme.commons.message.service.schema

import org.acme.commons.message.MessageDeadLetter
import org.acme.commons.message.avro.DeadLetterTransmit
import reactor.core.publisher.Mono

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface DeadLetterMessageSenderService: SchemaMessageSenderService<DeadLetterTransmit> {

    fun send(
        topic: String,
        key: String,
        payload: MessageDeadLetter
    ): Mono<Unit>
}