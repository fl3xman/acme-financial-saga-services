package org.acme.financial.payments.event

import org.acme.commons.logging.provideLogger
import org.acme.commons.message.service.MessageReceiverService
import org.acme.commons.message.service.schema.SchemaMessageReceiverService
import org.acme.financial.payments.avro.SinglePaymentResultTransmit
import org.acme.financial.payments.dto.PaymentResultDTO
import org.acme.financial.payments.service.PaymentService
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
    @Autowired private val paymentService: PaymentService,
    @Autowired private val schemaMessageReceiverService: SchemaMessageReceiverService<SinglePaymentResultTransmit>,
    @Value("\${acme.payment.topics.single-payment-completed}") private val topic: String,
    @Value("\${acme.payment.topics.single-payment-completed-dlq}") private val topicDLQ: String
) {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    @EventListener(ApplicationReadyEvent::class)
    fun onReady() {
        schemaMessageReceiverService.on(Pair(topic, topicDLQ)) {
            logger.debug("Received payment result event with data=$it")
            paymentService.processPaymentResult(PaymentResultDTO(it))
        }.subscribe()
    }
}

