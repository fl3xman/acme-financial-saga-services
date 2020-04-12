package org.acme.commons.outbox.event

import com.fasterxml.jackson.databind.ObjectMapper
import org.acme.commons.domain.AggregateIdentity
import org.acme.commons.logging.provideLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.stereotype.Component
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Component
class OutboxEventProducer(
    @Autowired private val objectMapper: ObjectMapper
): ApplicationEventPublisherAware {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    private var eventPublisher: ApplicationEventPublisher? = null

    override fun setApplicationEventPublisher(publisher: ApplicationEventPublisher) {
        eventPublisher = publisher
    }

    fun <T: AggregateIdentity<UUID>> publish(topic:String, event:String, payload: T) {
        try {
            eventPublisher?.publishEvent(
                OutboxEvent(
                    payload.id, topic, event, objectMapper.writeValueAsString(payload)
                )
            )
        } catch (exception: Throwable) {
            logger.error("Outbox event can't be processed: $exception")
        }
    }
}