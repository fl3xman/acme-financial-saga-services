package org.acme.financial.accounts.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Configuration
class MappingConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper = ObjectMapper()
}