package org.acme.commons.logging.exchannge.filter

import org.acme.commons.logging.provideLogger
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.web.server.*
import reactor.core.publisher.Mono

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

class ExchangeLoggingFilter(
    private val allowLogHeaders: Boolean = false,
    private val excludedPatterns: Regex? = null
) : WebFilter {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        return excludedPatterns?.let {
            if (exchange.request.uri.path.matches(it)) chain.filter(exchange)
            else processLogging(exchange, chain)
        } ?: processLogging(exchange, chain)
    }

    private fun processLogging(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val time = System.currentTimeMillis()

        if (!
            exchange.request.headers.containsKey(HttpHeaders.CONTENT_LENGTH) ||
            exchange.request.headers.contentLength == 0L
        ) {
            provideRequestLog(
                logger,
                exchange.request,
                allowLogHeaders
            )
        }

        return chain.filter(object : ServerWebExchangeDecorator(exchange) {

            override fun getRequest(): ServerHttpRequest =
                RequestLoggingInterceptor(
                    super.getRequest(),
                    logger,
                    allowLogHeaders
                )

            override fun getResponse(): ServerHttpResponse =
                ResponseLoggingInterceptor(
                    super.getResponse(),
                    logger,
                    time,
                    allowLogHeaders
                )

        }).doOnError {
            when (it) {
                is ResponseStatusException -> provideResponseErrorLog(logger, it, time, allowLogHeaders)
                else -> {
                    logger.error("Unknown response exception: $it")
                }
            }
        }
    }
}