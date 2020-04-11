package org.acme.commons.outbox.event

import org.acme.commons.logging.provideLogger
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@EnableScheduling
@Component
class OutboxEventScheduledRelay {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 5000)
    fun schedule() {
        logger.info("Outbox event scheduled!")
    }
}