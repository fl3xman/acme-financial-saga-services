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
import org.springframework.transaction.annotation.Transactional
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
        processById(id).toMono()
    }

    override fun flushByAggregateId(id: UUID): Mono<Unit> = Mono.defer {
        processByAggregateId(id).toMono()
    }

    override fun flushAll(): Mono<Unit> = Mono.defer {
        processAll().toMono()
    }

    override fun deleteAll(): Mono<Unit> = Mono.defer {
        outboxRepository.deleteAll().toMono()
    }

    override fun deleteById(id: UUID): Mono<Unit> = Mono.defer {
        outboxRepository.deleteById(id).toMono()
    }

    override fun deleteByAggregateId(id: UUID): Mono<Unit> = Mono.defer {
        outboxRepository.deleteByAggregateId(id).toMono()
    }

    override fun findByAggregateId(id: UUID): Mono<OutboxDTO> = Mono.defer {
        outboxRepository.findByAggregateId(id)?.let {
            OutboxDTO(it)
        }?.toMono() ?: throw EntityNotFoundException()
    }

    override fun findAll(): Flux<OutboxDTO> = Flux.defer {
        outboxRepository.findAll().map { OutboxDTO(it) }.toFlux()
    }

    internal fun processById(id: UUID) {
        val outbox = outboxRepository.findByIdOrNull(id)
        outbox?.let {
            messageService.send(OutboxDTO(it))
            outboxRepository.delete(it)
        } ?: throw EntityNotFoundException()
    }

    internal fun processByAggregateId(id: UUID) {
        val outbox = outboxRepository.findByAggregateId(id)
        outbox?.let {
            messageService.send(OutboxDTO(it))
            outboxRepository.delete(it)
        } ?: throw EntityNotFoundException()
    }

    @Transactional
    internal fun processAll() {
        val results = outboxRepository.findAll()

        messageService.bulkSend(results.map { OutboxDTO(it) })
        outboxRepository.deleteInBatch(results)
    }
}