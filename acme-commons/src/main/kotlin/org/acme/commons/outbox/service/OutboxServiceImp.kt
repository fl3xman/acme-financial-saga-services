package org.acme.commons.outbox.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.acme.commons.domain.AggregateIdentity
import org.acme.commons.message.service.MessageService
import org.acme.commons.outbox.domain.Outbox
import org.acme.commons.outbox.dto.OutboxDTO
import org.acme.commons.outbox.repository.OutboxRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
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

@Service
class OutboxServiceImp(
    @Autowired private val outboxRepository: OutboxRepository,
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired private val messageService: MessageService
) : OutboxService {

    override fun <T : AggregateIdentity<UUID>> append(topic: String, payload: T) {
        outboxRepository.save(Outbox(topic, payload, objectMapper))
    }

    override fun flushById(id: UUID): Mono<Unit> = Mono.defer {
        outboxRepository.findByIdOrNull(id)?.let {
            messageService
                .send(OutboxDTO(it))
                .doOnNext { result ->
                    result.getOrNull()?.let { id ->
                        outboxRepository.deleteById(id)
                    }
                }.map { Unit }
        } ?: Mono.error(EntityNotFoundException())
    }

    override fun flushAll(): Flux<Unit> = Flux.defer {
        messageService
            .bulkSend(outboxRepository.findAll().map { OutboxDTO(it) })
            .doOnNext { result ->
                result.getOrNull()?.let { id ->
                    outboxRepository.deleteById(id)
                }
            }.map { Unit }
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