package org.acme.financial.accounts.configuration

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
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
    fun objectMapper(): ObjectMapper = jacksonObjectMapper().also {
        it.registerModule(MoneyModule())
        it.registerModule(JavaTimeModule())
        it.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
}