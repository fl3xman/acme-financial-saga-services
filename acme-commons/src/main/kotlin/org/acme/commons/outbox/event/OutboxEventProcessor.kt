package org.acme.commons.outbox.event

import org.acme.commons.logging.provideLogger
import org.acme.commons.outbox.service.OutboxService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Component
class OutboxEventProcessor(
    @Autowired private val outboxService: OutboxService
) {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 5000)
    fun schedule() {
        /*
        outboxService.flushAll().doOnError {
            logger.error("Outbox event flush failed with error: $it")
        }.subscribe()

         */
    }
}