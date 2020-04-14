package org.acme.commons.message.service

import reactor.core.publisher.Flux

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface MessageReceiverService {
    fun <T> on(topic: String, mappingClass: Class<T>, acknowledged: (T) -> Boolean): Flux<T>
}