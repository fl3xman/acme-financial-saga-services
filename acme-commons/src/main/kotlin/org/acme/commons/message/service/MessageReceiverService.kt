package org.acme.commons.message.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

interface MessageReceiverService {
    fun <T> on(
        topic: String,
        mappingClass: Class<T>,
        process: (T) -> Mono<Unit>
    ): Flux<Unit> =
        on(Pair(topic, null), mappingClass, process)

    fun <T> on(
        topicWithDLQ: Pair<String, String?>,
        mappingClass: Class<T>,
        process: (T) -> Mono<Unit>
    ): Flux<Unit> =
        on(topicWithDLQ, 6, Duration.ofSeconds(20), mappingClass, process)

    fun <T> on(
        topicWithDLQ: Pair<String, String?>,
        maxRetry: Long, duration: Duration,
        mappingClass: Class<T>,
        process: (T) -> Mono<Unit>
    ): Flux<Unit>
}