package org.acme.financial.payments.configuration

import org.acme.commons.actuator.ActuatorRoute
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
    @Value("\${acme.payment.security.user.account-id}") private val accountId: String,
    @Value("\${acme.payment.security.user.username}") private val username: String,
    @Value("\${acme.payment.security.user.password}") private val password: String,
    @Value("\${acme.payment.security.user.role}") private val role: String
) {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain =
        http.formLogin().disable()
            .csrf().disable()
            .logout().disable()
            .httpBasic()
            .and()
            .authorizeExchange()
            .pathMatchers(ActuatorRoute.ANY).permitAll()
            .anyExchange().authenticated()
            .and()
            .build()

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    @Bean
    fun userDetailsService(): ReactiveUserDetailsService = InMemoryAccountIdentityAwareUserDetailService(
        AccountIdentityAwareUser<UUID>(
            UUID.fromString(accountId),
            User
                .withUsername(username)
                .password(passwordEncoder().encode(password))
                .roles(role)
                .build()
        )
    )
}