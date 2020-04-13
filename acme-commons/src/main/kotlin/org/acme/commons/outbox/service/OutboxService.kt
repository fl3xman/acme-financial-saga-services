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

    fun flushById(id: UUID): Mono<Unit>
    fun flushByAggregateId(id: UUID): Mono<Unit>
    fun flushAll(): Mono<Unit>

    fun deleteById(id: UUID): Mono<Unit>
    fun deleteByAggregateId(id: UUID): Mono<Unit>
    fun deleteAll(): Mono<Unit>

    fun findByAggregateId(id: UUID): Mono<OutboxDTO>
    fun findAll(): Flux<OutboxDTO>

    fun <T : AggregateIdentity<UUID>> append(command: OutboxCommand<T>) = append(command.topic, command.payload)
    fun <T : AggregateIdentity<UUID>> append(topic: String, payload: T)
}