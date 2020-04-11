package org.acme.commons.logging.exchannge.filter

import org.reactivestreams.Publisher
import org.slf4j.Logger
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.http.server.reactive.ServerHttpResponseDecorator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.channels.Channels

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

internal class ResponseLoggingInterceptor(
    delegate: ServerHttpResponse,
    private val logger: Logger,
    private val time: Long,
    private val allowLogHeaders: Boolean = false
): ServerHttpResponseDecorator(delegate) {

    override fun writeWith(body: Publisher<out DataBuffer>): Mono<Void> = super.writeWith(Flux.from(body)
        .doOnNext { dataBuffer ->
            val payloadStream = ByteArrayOutputStream()
            try {
                Channels.newChannel(payloadStream).write(dataBuffer.asByteBuffer().asReadOnlyBuffer())
                val payload: String = payloadStream.toByteArray().toString(Charsets.UTF_8)

                provideResponseLog(
                    logger,
                    delegate,
                    time,
                    allowLogHeaders,
                    payload
                )

            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    payloadStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        })
}

