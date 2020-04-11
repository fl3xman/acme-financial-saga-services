package org.acme.commons.kafka.configuration

import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Configuration
class KafkaConsumerConfiguration {

    @Bean
    fun kafkaReceiver(kafkaProperties: KafkaProperties): KafkaReceiver<String, String> = KafkaReceiver.create(
        ReceiverOptions.create(kafkaProperties.buildConsumerProperties())
    )
}