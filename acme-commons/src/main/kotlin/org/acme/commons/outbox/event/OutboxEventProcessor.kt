package org.acme.commons.outbox.event

import org.acme.commons.outbox.domain.Outbox
import org.acme.commons.outbox.repository.OutboxRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Component
class OutboxEventProcessor(
    @Autowired private val outboxRepository: OutboxRepository
) {
    @EventListener
    fun onAppend(event: OutboxEvent) {
        outboxRepository.save(Outbox(event))
    }
}