package org.acme.commons.security

import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
class InMemoryAccountIdentityAwareUserDetailService(private val users: List<UserDetails>) : ReactiveUserDetailsService {
    constructor(user: UserDetails) : this(listOf(user))

    override fun findByUsername(username: String?): Mono<UserDetails> {
        return username?.let { u -> Flux.fromIterable(users).filter { it.username == u }.next() } ?: Mono.empty()
    }
}