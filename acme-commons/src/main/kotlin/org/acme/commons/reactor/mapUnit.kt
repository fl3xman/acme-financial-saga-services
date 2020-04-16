package org.acme.commons.reactor

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

fun Flux<*>.mapUnit(): Flux<Unit> = map { Unit }
fun Mono<*>.mapUnit(): Mono<Unit> = map { Unit }