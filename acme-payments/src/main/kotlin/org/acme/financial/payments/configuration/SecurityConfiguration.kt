package org.acme.financial.payments.configuration

import org.acme.commons.actuator.ActuatorRoute
import org.acme.commons.openapi.OpenapiRoute
import org.acme.commons.security.AccountIdentityAwareUser
import org.acme.commons.security.InMemoryAccountIdentityAwareUserDetailService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Configuration
@EnableWebFluxSecurity
class SecurityConfiguration(
    // Primary user
    @Value("\${acme.payment.security.users.user-primary.account-id}") private val primaryAccountId: String,
    @Value("\${acme.payment.security.users.user-primary.username}") private val primaryUsername: String,
    @Value("\${acme.payment.security.users.user-primary.password}") private val primaryPassword: String,
    @Value("\${acme.payment.security.users.user-primary.role}") private val primaryRole: String,
    // Secondary user
    @Value("\${acme.payment.security.users.user-secondary.account-id}") private val secondaryAccountId: String,
    @Value("\${acme.payment.security.users.user-secondary.username}") private val secondaryUsername: String,
    @Value("\${acme.payment.security.users.user-secondary.password}") private val secondaryPassword: String,
    @Value("\${acme.payment.security.users.user-secondary.role}") private val secondaryRole: String

) {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        http.formLogin().disable()
            .csrf().disable()
            .logout().disable()
            .httpBasic()
            .and()
            .authorizeExchange()
            .pathMatchers(ActuatorRoute.ANY, *OpenapiRoute.ANY).permitAll()
            .anyExchange().authenticated()
            .and()
            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun userDetailsService(): ReactiveUserDetailsService = InMemoryAccountIdentityAwareUserDetailService(
        listOf(
            AccountIdentityAwareUser<UUID>(
                UUID.fromString(primaryAccountId),
                User
                    .withUsername(primaryUsername)
                    .password(passwordEncoder().encode(primaryPassword))
                    .roles(primaryRole)
                    .build()
            ),
            AccountIdentityAwareUser<UUID>(
                UUID.fromString(secondaryAccountId),
                User
                    .withUsername(secondaryUsername)
                    .password(passwordEncoder().encode(secondaryPassword))
                    .roles(secondaryRole)
                    .build()
            )
        )
    )
}