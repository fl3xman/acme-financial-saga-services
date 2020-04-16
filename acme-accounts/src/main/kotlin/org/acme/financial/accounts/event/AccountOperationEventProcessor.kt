package org.acme.financial.accounts.event

import org.acme.commons.logging.provideLogger
import org.acme.commons.message.service.MessageReceiverService
import org.acme.financial.accounts.service.AccountOperationService
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
class AccountOperationEventProcessor(
    @Autowired private val accountOperationService: AccountOperationService,
    @Autowired private val messageReceiverService: MessageReceiverService,
    @Value("\${acme.account.topics.payment-transaction-started}") private val topic: String,
    @Value("\${acme.account.topics.payment-transaction-started-dlq}") private val topicDLQ: String
) {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    @EventListener(ApplicationReadyEvent::class)
    fun onReady() {
        messageReceiverService.on(Pair(topic, topicDLQ), AccountOperationEvent::class.java) {
            logger.debug("Received account operation start event=$it")
            accountOperationService.processAccountOperationStartedEvent(it)
        }.subscribe()
    }
}