package org.acme.financial.payments.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.jackson.datatype.money.MoneyModule

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Configuration
class MappingConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper().registerModule(MoneyModule());
}