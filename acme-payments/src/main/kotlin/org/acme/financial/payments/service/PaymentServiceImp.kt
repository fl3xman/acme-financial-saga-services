package org.acme.financial.payments.service

import org.acme.commons.outbox.service.OutboxService
import org.acme.financial.payments.command.PaymentCommand
import org.acme.financial.payments.domain.Payment
import org.acme.financial.payments.dto.PaymentDTO
import org.acme.financial.payments.exception.PaymentNotFoundException
import org.acme.financial.payments.exception.PaymentProcessingException
import org.acme.financial.payments.repository.PaymentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Service
class PaymentServiceImp(
    @Autowired private val paymentRepository: PaymentRepository,
    @Autowired private val outboxService: OutboxService,
    @Autowired private val transactionTemplate: TransactionTemplate,
    @Value("\${acme.payment.topics.payment-transaction-started}") private val topic: String
) : PaymentService {

    override fun create(input: PaymentCommand, accountId: UUID): Mono<PaymentDTO> = Mono.defer {
        createWithOutbox(input, accountId)?.let {
            PaymentDTO(it)
        }?.toMono() ?: Mono.error(PaymentProcessingException("Payment can't be created!"))
    }

    override fun getPayment(id: UUID, accountId: UUID): Mono<PaymentDTO> = Mono.defer {
        paymentRepository.findOneByIdAndAccountId(id, accountId)?.let {
            PaymentDTO(it)
        }?.toMono() ?: Mono.error(PaymentNotFoundException("Payment for id=$id does not exist!"))
    }

    override fun getPayments(accountId: UUID): Flux<PaymentDTO> = Flux.defer {
        paymentRepository.findAllByAccountId(accountId).map { PaymentDTO(it) }.toFlux()
    }

    private fun createWithOutbox(input: PaymentCommand, accountId: UUID): Payment? {
        return transactionTemplate.execute {
            paymentRepository.save(input.withAccountId(accountId).payment).also { payment ->
                outboxService.append(topic, payment)
            }
        }
    }
}
