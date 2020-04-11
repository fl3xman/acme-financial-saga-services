package org.acme.commons.outbox.service

import org.acme.commons.domain.AggregateIdentity
import org.acme.commons.outbox.dto.OutboxDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface OutboxService {

    fun findAll(): Flux<OutboxDTO>
    fun deleteAll(): Mono<Unit>

    fun <T: AggregateIdentity<UUID>> publish(topic:String, event:String, payload: T)
}