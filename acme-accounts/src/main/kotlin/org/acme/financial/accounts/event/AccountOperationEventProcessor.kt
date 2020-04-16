package org.acme.financial.accounts.event

import org.acme.commons.logging.provideLogger
import org.acme.commons.message.service.MessageReceiverService
import org.acme.financial.accounts.bo.AccountSinglePaymentBO
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
    @Value("\${acme.account.topics.single-payment-started}") private val topic: String,
    @Value("\${acme.account.topics.single-payment-started-dlq}") private val topicDLQ: String
) {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    @EventListener(ApplicationReadyEvent::class)
    fun onReady() {
        messageReceiverService.on(Pair(topic, topicDLQ), AccountSinglePaymentBO::class.java) {
            logger.debug("Received single payment event with data=$it")
            accountOperationService.processAccountSinglePayment(it)
        }.subscribe()
    }
}