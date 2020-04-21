package org.acme.financial.payments.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.acme.commons.message.service.schema.SchemaSenderService
import org.acme.commons.outbox.repository.OutboxRepository
import org.acme.commons.outbox.service.OutboxService
import org.acme.commons.outbox.service.schema.OutboxSchemaService
import org.acme.financial.payments.avro.PaymentSchema
import org.acme.financial.payments.avro.PaymentStatusSchema
import org.acme.financial.payments.dto.PaymentInternalDTO
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Configuration
class OutboxConfiguration {

    @Bean
    @Primary
    fun outboxService(
        outboxRepository: OutboxRepository,
        objectMapper: ObjectMapper,
        schemaSenderService: SchemaSenderService<PaymentSchema>
    ): OutboxService = OutboxSchemaService(
        outboxRepository, objectMapper, schemaSenderService
    ) {
        val payment = objectMapper.readValue(it, PaymentInternalDTO::class.java)
        PaymentSchema.newBuilder().apply {
            id = payment.id
            accountId = payment.accountId
            beneficiaryBuilder.apply {
                value = payment.beneficiary.value
                type = payment.beneficiary.type.value
            }
            transactionBuilder.apply {
                amount = payment.transaction.amount
                currency = payment.transaction.currency
            }
            status = PaymentStatusSchema.valueOf(payment.status.value)
        }.build()
    }
}