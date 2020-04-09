package org.acme.commons.logging

import org.slf4j.Logger
import org.springframework.http.server.reactive.ServerHttpRequest

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

internal fun provideRequestLog(logger: Logger, delegate: ServerHttpRequest, allowLogHeaders: Boolean, body: String? = null) {
    val headers = if(allowLogHeaders) ", headers=${delegate.headers}" else ""
    val payload = body?.let { ", payload=$it" } ?: ""

    logger.info(
        "Request: method=${delegate.method}, uri=${delegate.path}$headers$payload"
    )
}