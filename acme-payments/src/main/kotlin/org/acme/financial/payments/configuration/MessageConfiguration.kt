package org.acme.financial.payments.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.acme.commons.kafka.service.schema.DeadLetterSenderService
import org.acme.commons.kafka.service.schema.KafkaSchemaReceiverService
import org.acme.commons.kafka.service.schema.KafkaSchemaSenderService
import org.acme.commons.message.avro.DeadLetterSchema
import org.acme.commons.message.service.schema.SchemaReceiverService
import org.acme.commons.message.service.schema.SchemaSenderService
import org.acme.financial.payments.avro.PaymentResultSchema
import org.acme.financial.payments.avro.PaymentSchema
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
    fun schemaTemplate(kafkaProperties: KafkaProperties): ReactiveKafkaProducerTemplate<String, PaymentSchema> =
        ReactiveKafkaProducerTemplate(
            SenderOptions.create(kafkaProperties.buildProducerProperties())
        )

    @Bean
    fun schemaSenderService(
        schemaTemplate: ReactiveKafkaProducerTemplate<String, PaymentSchema>
    ): SchemaSenderService<PaymentSchema> = KafkaSchemaSenderService(schemaTemplate)

    @Bean
    fun deadLetterTemplate(kafkaProperties: KafkaProperties): ReactiveKafkaProducerTemplate<String, DeadLetterSchema> =
        ReactiveKafkaProducerTemplate(
            SenderOptions.create(kafkaProperties.buildProducerProperties())
        )

    @Bean
    fun deadLetterSenderService(
        deadLetterTemplate: ReactiveKafkaProducerTemplate<String, DeadLetterSchema>
    ): DeadLetterSenderService = DeadLetterSenderService(deadLetterTemplate)

    @Bean
    fun schemaReceiverService(
        kafkaProperties: KafkaProperties,
        objectMapper: ObjectMapper,
        deadLetterSenderService: DeadLetterSenderService
    ): SchemaReceiverService<PaymentResultSchema> =
        KafkaSchemaReceiverService<PaymentResultSchema>(
            kafkaProperties,
            objectMapper,
            deadLetterSenderService
        )
}