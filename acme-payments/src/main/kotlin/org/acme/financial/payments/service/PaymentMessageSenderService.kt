package org.acme.financial.payments.service

import org.acme.commons.kafka.service.schema.KafkaSchemaMessageSenderService
import org.acme.financial.payments.avro.SinglePaymentTransmit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
class PaymentMessageSenderService(
    @Autowired schemaProducerTemplate: ReactiveKafkaProducerTemplate<String, SinglePaymentTransmit>
): KafkaSchemaMessageSenderService<SinglePaymentTransmit>(schemaProducerTemplate)