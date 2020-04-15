package org.acme.commons.message.service

import org.acme.commons.domain.AggregateIdentity
import org.acme.commons.domain.Identity
import org.acme.commons.message.MessagePayloadAware
import org.acme.commons.message.MessageTopicAware
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

interface MessageSenderService {

    // Send blocking

    fun <T> send(
        payload: T,
        complete: (Result<UUID>) -> Unit
    ) where T : Identity<UUID>,
            T : AggregateIdentity<UUID>,
            T : MessageTopicAware,
            T : MessagePayloadAware = bulkSend(listOf(payload), complete)

    fun <T> bulkSend(
        payloads: List<T>,
        complete: (Result<UUID>) -> Unit
    ) where T : Identity<UUID>,
            T : AggregateIdentity<UUID>,
            T : MessageTopicAware,
            T : MessagePayloadAware

    // Send blocking raw

    fun send(
        topic: String,
        key: String,
        payload: String,
        complete: (Result<Unit>) -> Unit
    )

    // Send non-blocking

    fun <T> send(
        payload: T
    ): Mono<Result<UUID>> where T : Identity<UUID>,
                                T : AggregateIdentity<UUID>,
                                T : MessageTopicAware,
                                T : MessagePayloadAware = bulkSend(listOf(payload)).next()

    fun <T> bulkSend(
        payloads: List<T>
    ): Flux<Result<UUID>> where T : Identity<UUID>,
                                T : AggregateIdentity<UUID>,
                                T : MessageTopicAware,
                                T : MessagePayloadAware

    // Send non-blocking raw

    fun send(
        topic: String,
        key: String,
        payload: String
    ): Mono<Unit>
}