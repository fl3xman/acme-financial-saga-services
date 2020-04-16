package org.acme.commons.kafka.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.acme.commons.logging.provideLogger
import org.acme.commons.message.MessageDeadLetter
import org.acme.commons.message.service.MessageReceiverService
import org.acme.commons.message.service.MessageSenderService
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

class KafkaMessageReceiverService(
    @Autowired private val kafkaProperties: KafkaProperties,
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired private val messageSenderService: MessageSenderService
) : MessageReceiverService {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    private val scheduler = Schedulers.newElastic("org.acme.commons.processing", 10, true);
    private val topics = mutableMapOf<String, ReactiveKafkaConsumerTemplate<String, String>>()

    override fun <T> on(
        topicWithDLQ: Pair<String, String?>,
        maxRetry: Long, duration: Duration,
        mappingClass: Class<T>,
        process: (T) -> Mono<Unit>
    ): Flux<Unit> = Flux.defer {
        templateBy(topicWithDLQ.first)
            .receive()
            .groupBy { it.receiverOffset().topicPartition() }
            .publishOn(scheduler)
            .flatMap {
                it.publishOn(scheduler).concatMap { record ->
                    Flux.just(record).concatMap { processedRecord ->
                        Mono.defer<Unit> {
                            try {
                                logger.debug("Record processing started for key=${processedRecord.key()} and payload=${processedRecord.value()}")
                                process(objectMapper.readValue(processedRecord.value(), mappingClass))
                            } catch (exception: Throwable) {
                                Mono.error(exception)
                            }
                        }
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
                        redriveBy(topicWithDLQ.second, record, exception).then()
                    }
                }
            }.mapUnit()
    }

    private fun redriveBy(
        topicDLQ: String?,
        record: ReceiverRecord<String, String>,
        exception: Throwable
    ): Mono<Unit> {
        return topicDLQ?.let {
            messageSenderService.send(it, record.key(), objectMapper.writeValueAsString(
                MessageDeadLetter(exception, record.value())
            ))
        } ?: Mono.empty()
    }

    private fun templateBy(topic: String): ReactiveKafkaConsumerTemplate<String, String> {
        return topics.getOrPut(topic) {
            ReactiveKafkaConsumerTemplate(
                ReceiverOptions.create<String, String>(kafkaProperties.buildConsumerProperties()).also {
                    it.subscription(listOf(topic))
                }
            )
        }
    }
}