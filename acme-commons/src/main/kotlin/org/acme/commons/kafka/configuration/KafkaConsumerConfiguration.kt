package org.acme.commons.kafka.configuration

import org.springframework.context.annotation.Configuration

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Configuration
class KafkaConsumerConfiguration {
    /*
    @Bean
    fun kafkaReceiver(kafkaProperties: KafkaProperties): KafkaReceiver<String, String> = KafkaReceiver.create(
        ReceiverOptions.create(kafkaProperties.buildConsumerProperties())
    )*/
}