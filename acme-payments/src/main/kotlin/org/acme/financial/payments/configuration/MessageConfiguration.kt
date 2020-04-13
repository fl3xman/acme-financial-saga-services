package org.acme.financial.payments.configuration

import org.acme.commons.kafka.service.KafkaMessageService
import org.acme.commons.message.service.MessageService
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
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
    fun producerFactory(kafkaProperties: KafkaProperties): ProducerFactory<String, String> = DefaultKafkaProducerFactory(
        kafkaProperties.buildProducerProperties()
    )

    @Bean
    fun kafkaTemplate(kafkaProperties: KafkaProperties): KafkaTemplate<String, String> = KafkaTemplate(
        producerFactory(kafkaProperties)
    )

    @Bean
    fun kafkaProducerTemplate(kafkaProperties: KafkaProperties): ReactiveKafkaProducerTemplate<String, String> = ReactiveKafkaProducerTemplate(
        SenderOptions.create(kafkaProperties.buildProducerProperties())
    )

    @Bean
    fun messageService(
        kafkaTemplate: KafkaTemplate<String, String>,
        kafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, String>
    ): MessageService = KafkaMessageService(kafkaTemplate, kafkaProducerTemplate)
}