package org.acme.financial.accounts.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.acme.commons.kafka.service.KafkaMessageReceiverService
import org.acme.commons.kafka.service.KafkaMessageSenderService
import org.acme.commons.message.service.MessageReceiverService
import org.acme.commons.message.service.MessageSenderService
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
    fun kafkaProducerTemplate(kafkaProperties: KafkaProperties): ReactiveKafkaProducerTemplate<String, String> =
        ReactiveKafkaProducerTemplate(
            SenderOptions.create(kafkaProperties.buildProducerProperties())
        )

    @Bean
    fun messageSenderService(
        kafkaProducerTemplate: ReactiveKafkaProducerTemplate<String, String>
    ): MessageSenderService = KafkaMessageSenderService(kafkaProducerTemplate)

    @Bean
    fun messageReceiverService(
        kafkaProperties: KafkaProperties,
        objectMapper: ObjectMapper,
        messageSenderService: MessageSenderService
    ): MessageReceiverService = KafkaMessageReceiverService(kafkaProperties, objectMapper, messageSenderService)
}