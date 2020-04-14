package org.acme.financial.payments.event

import org.acme.commons.logging.provideLogger
import org.acme.commons.message.service.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Component
class PaymentEventProcessor(
    @Autowired private val messageService: MessageService,
    @Value("\${acme.payment.topics.payment-transaction-completed}") private val topic: String
) {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    @EventListener(ApplicationReadyEvent::class)
    fun onReady() {
        logger.info("Payment processor ready!")
    }
}

