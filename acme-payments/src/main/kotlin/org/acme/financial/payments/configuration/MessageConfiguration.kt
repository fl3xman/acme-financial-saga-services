package org.acme.financial.payments.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.acme.commons.kafka.service.KafkaMessageReceiverService
import org.acme.commons.kafka.service.KafkaMessageSenderService
import org.acme.commons.kafka.service.schema.KafkaDeadLetterMessageSenderService
import org.acme.commons.kafka.service.schema.KafkaSchemaMessageReceiverService
import org.acme.commons.message.avro.DeadLetterTransmit
import org.acme.commons.message.service.MessageReceiverService
import org.acme.commons.message.service.MessageSenderService
import org.acme.commons.message.service.schema.DeadLetterMessageSenderService
import org.acme.commons.message.service.schema.SchemaMessageReceiverService
import org.acme.commons.message.service.schema.SchemaMessageSenderService
import org.acme.financial.payments.avro.SinglePaymentResultTransmit
import org.acme.financial.payments.avro.SinglePaymentTransmit
import org.acme.financial.payments.service.PaymentMessageReceiverService
import org.acme.financial.payments.service.PaymentMessageSenderService
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import reactor.kafka.sender.SenderOptions

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Configuration
class MessageConfiguration {

    @Bean
    fun schemaProducerTemplate(kafkaProperties: KafkaProperties): ReactiveKafkaProducerTemplate<String, SinglePaymentTransmit> =
        ReactiveKafkaProducerTemplate(
            SenderOptions.create(kafkaProperties.buildProducerProperties())
        )

    @Bean
    fun deadLetterProducerTemplate(kafkaProperties: KafkaProperties): ReactiveKafkaProducerTemplate<String, DeadLetterTransmit> =
        ReactiveKafkaProducerTemplate(
            SenderOptions.create(kafkaProperties.buildProducerProperties())
        )

    @Bean
    fun schemaMessageSenderService(
        schemaProducerTemplate: ReactiveKafkaProducerTemplate<String, SinglePaymentTransmit>
    ): SchemaMessageSenderService<SinglePaymentTransmit> = PaymentMessageSenderService(schemaProducerTemplate)

    @Bean
    fun deadLetterMessageSenderService(
        deadLetterProducerTemplate: ReactiveKafkaProducerTemplate<String, DeadLetterTransmit>
    ): DeadLetterMessageSenderService = KafkaDeadLetterMessageSenderService(deadLetterProducerTemplate)

    @Bean
    fun schemaMessageReceiverService(
        kafkaProperties: KafkaProperties,
        objectMapper: ObjectMapper,
        deadLetterMessageSenderService: DeadLetterMessageSenderService
    ): SchemaMessageReceiverService<SinglePaymentResultTransmit> = PaymentMessageReceiverService(kafkaProperties, objectMapper, deadLetterMessageSenderService)
}