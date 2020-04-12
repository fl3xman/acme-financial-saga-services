package org.acme.commons.logging.exchannge.filter

import org.slf4j.Logger
import org.springframework.web.server.ResponseStatusException

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

internal fun provideResponseErrorLog(logger: Logger, exception: ResponseStatusException, time: Long, allowLogHeaders: Boolean) {
    val headers = if(allowLogHeaders) ", headers=${exception.responseHeaders}" else ""
    val payload = exception.cause?.let { ", payload=$it" } ?: ""

    logger.info(
        "Response(${System.currentTimeMillis() - time} ms): status=${exception.status}$headers$payload"
    )
}