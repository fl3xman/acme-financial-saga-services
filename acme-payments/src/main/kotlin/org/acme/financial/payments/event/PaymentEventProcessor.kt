package org.acme.financial.payments.event

import org.acme.commons.event.ReadyEventHandler
import org.acme.commons.logging.provideLogger
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Component
class PaymentEventProcessor: ReadyEventHandler {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    @EventListener(ApplicationReadyEvent::class)
    override fun onReady() {

        logger.info("Payment processor ready!")
    }
}

