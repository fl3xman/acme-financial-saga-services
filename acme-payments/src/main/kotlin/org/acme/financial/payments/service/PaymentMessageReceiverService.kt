package org.acme.financial.payments.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.acme.commons.kafka.service.schema.KafkaSchemaMessageReceiverService
import org.acme.commons.message.service.schema.DeadLetterMessageSenderService
import org.acme.financial.payments.avro.SinglePaymentResultTransmit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.kafka.KafkaProperties

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
class PaymentMessageReceiverService(
    @Autowired kafkaProperties: KafkaProperties,
    @Autowired objectMapper: ObjectMapper,
    @Autowired deadLetterMessageSenderService: DeadLetterMessageSenderService
): KafkaSchemaMessageReceiverService<SinglePaymentResultTransmit>(kafkaProperties, objectMapper, deadLetterMessageSenderService)