package org.acme.financial.accounts.event

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
class AccountEventProcessor: ReadyEventHandler {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    @EventListener(ApplicationReadyEvent::class)
    override fun onReady() {

        logger.info("Account processor ready!")
    }
}