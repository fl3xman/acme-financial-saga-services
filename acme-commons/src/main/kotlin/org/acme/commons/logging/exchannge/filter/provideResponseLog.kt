package org.acme.commons.logging.exchannge.filter

import org.slf4j.Logger
import org.springframework.http.server.reactive.ServerHttpResponse

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

internal fun provideResponseLog(logger: Logger, delegate: ServerHttpResponse, time: Long, allowLogHeaders: Boolean, body: String? = null) {
    val headers = if(allowLogHeaders) ", headers=${delegate.headers}" else ""
    val payload = body?.let { ", payload=$it" } ?: ""

    logger.info(
        "Response(${System.currentTimeMillis() - time} ms): status=${delegate.statusCode}$headers$payload"
    )
}