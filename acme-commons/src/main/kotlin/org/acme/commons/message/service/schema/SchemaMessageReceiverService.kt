package org.acme.commons.message.service.schema

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface SchemaMessageReceiverService<Schema> {

    fun on(
        topic: String,
        process: (Schema) -> Mono<Unit>
    ): Flux<Unit> = on(Pair(topic, null), process)

    fun on(
        topicWithDLQ: Pair<String, String?>,
        process: (Schema) -> Mono<Unit>
    ): Flux<Unit> =
        on(topicWithDLQ, 6, Duration.ofSeconds(20), process)

    fun on(
        topicWithDLQ: Pair<String, String?>,
        maxRetry: Long, duration: Duration,
        process: (Schema) -> Mono<Unit>
    ): Flux<Unit>
}