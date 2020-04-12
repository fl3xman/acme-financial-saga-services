package org.acme.commons.outbox.service

import org.acme.commons.domain.AggregateIdentity
import org.acme.commons.outbox.dto.OutboxDTO
import org.acme.commons.outbox.event.OutboxEventProducer
import org.acme.commons.outbox.repository.OutboxRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Service
class OutboxServiceImp(
    @Autowired private val outboxRepository: OutboxRepository,
    @Autowired private val outboxEventProducer: OutboxEventProducer
): OutboxService {

    override fun <T: AggregateIdentity<UUID>> execute(topic:String, event:String, payload: T) {
        outboxEventProducer.publish(topic, event, payload) // TODO check TRX
    }

    override fun findAll(): Flux<OutboxDTO> = Flux.defer {
        outboxRepository.findAll().map { OutboxDTO(it) }.toFlux()
    }

    override fun deleteAll(): Mono<Unit> = Mono.defer {
        outboxRepository.deleteAll().toMono()
    }
}