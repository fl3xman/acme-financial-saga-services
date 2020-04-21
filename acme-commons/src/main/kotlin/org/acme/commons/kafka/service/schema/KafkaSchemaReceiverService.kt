package org.acme.commons.kafka.service.schema

import com.fasterxml.jackson.databind.ObjectMapper
import org.acme.commons.logging.provideLogger
import org.acme.commons.message.DeadLetter
import org.acme.commons.message.service.schema.SchemaReceiverService
import org.acme.commons.reactor.mapUnit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.kafka.receiver.ReceiverOptions
import reactor.kafka.receiver.ReceiverRecord
import reactor.util.retry.Retry
import java.time.Duration

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

open class KafkaSchemaReceiverService<Schema>(
    @Autowired private val kafkaProperties: KafkaProperties,
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired private val deadLetterSenderService: DeadLetterSenderService
): SchemaReceiverService<Schema> {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    private val scheduler = Schedulers.newElastic("org.acme.commons.processing", 10, true);
    private val topics = mutableMapOf<String, ReactiveKafkaConsumerTemplate<String, Schema>>()

    override fun on(
        topicWithDLQ: Pair<String, String?>,
        maxRetry: Long, duration: Duration,
        process: (Schema) -> Mono<Unit>
    ): Flux<Unit> = Flux.defer {
        templateBy(topicWithDLQ.first)
            .receive()
            .groupBy { it.receiverOffset().topicPartition() }
            .publishOn(scheduler)
            .flatMap {
                it.publishOn(scheduler).concatMap { record ->
                    Flux.just(record).concatMap { processedRecord ->
                        process(processedRecord.value())
                            .then(processedRecord.receiverOffset().commit())
                            .doOnSuccess {
                                logger.debug("Record processed successfully")
                            }
                            .doOnError { exception ->
                                logger.error("Record processing failed with error=$exception")
                            }
                    }.retryWhen(
                        Retry.backoff(maxRetry, duration)
                            .onRetryExhaustedThrow { _, retry ->
                                logger.error("Record processing will acknowledge redrive offset=${record.receiverOffset().offset()}")

                                record.receiverOffset().acknowledge()
                                retry.failure()
                            }
                    ).onErrorResume { exception ->
                        logger.error("Record processing will redrive after retries=${maxRetry} with error=${exception}")
                        topicWithDLQ.second?.let { dlq ->
                            redriveBy(dlq, record, exception).then()
                        } ?: Mono.empty()

                    }
                }
            }.mapUnit()
    }

    private fun redriveBy(
        topicDLQ: String,
        record: ReceiverRecord<String, Schema>,
        exception: Throwable
    ): Mono<Unit> {
        return deadLetterSenderService.send(topicDLQ, record.key(), DeadLetter(
            exception, objectMapper.writeValueAsString(record.value())
        ))
    }

    private fun templateBy(topic: String): ReactiveKafkaConsumerTemplate<String, Schema> {
        return topics.getOrPut(topic) {
            ReactiveKafkaConsumerTemplate(
                ReceiverOptions.create<String, Schema>(kafkaProperties.buildConsumerProperties()).also {
                    it.subscription(listOf(topic))
                }
            )
        }
    }
}