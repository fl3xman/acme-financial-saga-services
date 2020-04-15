package org.acme.financial.accounts.service

import org.acme.commons.logging.provideLogger
import org.acme.commons.message.service.MessageReceiverService
import org.acme.commons.outbox.service.OutboxService
import org.acme.commons.reactor.mapUnit
import org.acme.financial.accounts.event.AccountOperationEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Service
class AccountOperationServiceImp(
    @Autowired private val outboxService: OutboxService,
    @Autowired private val messageReceiverService: MessageReceiverService,
    @Autowired private val transactionTemplate: TransactionTemplate,
    @Value("\${acme.account.topics.payment-transaction-started}") private val startedTopic: String,
    @Value("\${acme.account.topics.payment-transaction-started-dlq}") private val startedTopicDLQ: String,
    @Value("\${acme.account.topics.payment-transaction-completed}") private val completedTopic: String
): AccountOperationService {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    override fun onAccountOperationStarted(): Flux<Unit> = Flux.defer {
        messageReceiverService.on(Pair(startedTopic, startedTopicDLQ), AccountOperationEvent::class.java) {
            logger.debug("Received account operation starting event=$it")
            Mono.empty()
        }
    }
}