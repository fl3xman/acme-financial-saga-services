package org.acme.commons.logging.exchannge.filter

import org.slf4j.Logger
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import reactor.core.publisher.Flux
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.channels.Channels

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

internal class RequestLoggingInterceptor(
    delegate: ServerHttpRequest,
    private val logger: Logger,
    private val allowLogHeaders: Boolean = false
) : ServerHttpRequestDecorator(delegate) {

    override fun getBody(): Flux<DataBuffer> = super.getBody()
        .doOnNext { dataBuffer: DataBuffer ->
            val payloadStream = ByteArrayOutputStream()
            try {
                Channels.newChannel(payloadStream).write(dataBuffer.asByteBuffer().asReadOnlyBuffer())
                val payload: String = payloadStream.toByteArray().toString(Charsets.UTF_8)

                provideRequestLog(
                    logger,
                    delegate,
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
        }
}