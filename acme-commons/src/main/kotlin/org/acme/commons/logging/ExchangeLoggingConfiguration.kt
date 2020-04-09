package org.acme.commons.logging

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Configuration
class ExchangeLoggingConfiguration(
    @Value("\${logging.exchange.excludedPatterns:@null}") private var excludedPatterns: String? = null,
    @Value("\${logging.exchange.allowHeaders:@false}") private var allowHeaders: Boolean = false
) {
    @Bean
    fun exchangeLoggingFilter(): ExchangeLoggingFilter = ExchangeLoggingFilter(allowHeaders, excludedPatterns?.toRegex())
}