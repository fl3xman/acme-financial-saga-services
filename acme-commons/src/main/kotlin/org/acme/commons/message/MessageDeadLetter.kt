package org.acme.commons.message

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
data class MessageDeadLetter(
    val exception: String,
    val payload: String
) {
    constructor(exception: Throwable, payload: String): this(
        exception.cause?.message ?: (exception.message?: "unknown"), payload
    )
}