package org.acme.commons.outbox.service.schema

import com.fasterxml.jackson.databind.ObjectMapper
import org.acme.commons.domain.AggregateIdentity
import org.acme.commons.message.service.schema.SchemaSenderService
import org.acme.commons.outbox.domain.Outbox
import org.acme.commons.outbox.dto.OutboxDTO
import org.acme.commons.outbox.dto.schema.OutboxSchemaDTO
import org.acme.commons.outbox.repository.OutboxRepository
import org.acme.commons.outbox.service.OutboxService
import org.acme.commons.reactor.mapUnit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.*
import javax.persistence.EntityNotFoundException

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

class OutboxSchemaService<Schema>(
    @Autowired private val outboxRepository: OutboxRepository,
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired private val schemaSenderService: SchemaSenderService<Schema>,
    @Autowired private val converter: (payload: String) -> Schema
): OutboxService {

    override fun <T : AggregateIdentity<UUID>> append(topic: String, payload: T) {
        outboxRepository.save(Outbox(topic, payload, objectMapper))
    }

    override fun flushById(id: UUID): Mono<Unit> = Mono.defer {
        outboxRepository.findByIdOrNull(id)?.let {
            schemaSenderService
                .send(OutboxSchemaDTO(it, converter))
                .doOnNext { result ->
                    result.getOrNull()?.let { id ->
                        outboxRepository.deleteById(id)
                    }
                }.mapUnit()
        } ?: Mono.error(EntityNotFoundException())
    }

    override fun flushAll(): Flux<Unit> = Flux.defer {
        schemaSenderService
            .bulkSend(outboxRepository.findAll().map { OutboxSchemaDTO(it, converter) })
            .doOnNext { result ->
                result.getOrNull()?.let { id ->
                    outboxRepository.deleteById(id)
                }
            }.mapUnit()
    }

    override fun deleteAll(): Mono<Unit> = Mono.defer {
        outboxRepository.deleteAll().toMono()
    }

    override fun deleteById(id: UUID): Mono<Unit> = Mono.defer {
        outboxRepository.deleteById(id).toMono()
    }

    override fun findAll(): Flux<OutboxDTO> = Flux.defer {
        outboxRepository.findAll().map { OutboxDTO(it) }.toFlux()
    }
}