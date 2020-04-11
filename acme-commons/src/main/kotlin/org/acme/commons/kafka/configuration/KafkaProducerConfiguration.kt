package org.acme.commons.kafka.configuration

import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Bean
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderOptions


/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Configuration
class KafkaProducerConfiguration {

    @Bean
    fun kafkaSender(kafkaProperties: KafkaProperties): KafkaSender<String, String> = KafkaSender.create(
        SenderOptions.create(kafkaProperties.buildProducerProperties())
    )
}
