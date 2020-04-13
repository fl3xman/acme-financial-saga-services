package org.acme.financial.accounts.configuration

import org.acme.commons.kafka.service.KafkaMessageService
import org.acme.commons.message.service.MessageService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Configuration
class MessageConfiguration {

    @Bean
    fun messageService(
        kafkaTemplate: KafkaTemplate<String, String>,
        kafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, String>
    ): MessageService = KafkaMessageService(kafkaTemplate, kafkaProducerTemplate)
}