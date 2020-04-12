package org.acme.commons.outbox.service

import org.acme.commons.domain.AggregateIdentity
import org.acme.commons.outbox.command.OutboxCommand
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

    fun <T: AggregateIdentity<UUID>> execute(command: OutboxCommand<T>) = execute(command.topic, command.event, command.payload)
    fun <T: AggregateIdentity<UUID>> execute(topic:String, event:String, payload: T)
}