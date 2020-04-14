package org.acme.financial.payments.service

import org.acme.commons.outbox.service.OutboxService
import org.acme.financial.payments.command.PaymentCommand
import org.acme.financial.payments.domain.Payment
import org.acme.financial.payments.dto.PaymentDTO
import org.acme.financial.payments.exception.PaymentNotFoundException
import org.acme.financial.payments.repository.PaymentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.util.*
import javax.persistence.EntityNotFoundException

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Service
class PaymentServiceImp(
    @Autowired private val paymentRepository: PaymentRepository,
    @Autowired private val outboxService: OutboxService,
    @Value("\${acme.payment.topics.payment-transaction-started}") private val topic: String
) : PaymentService {

    override fun create(input: PaymentCommand, accountId: UUID): Mono<PaymentDTO> = Mono.defer {
        createWithOutbox(input, accountId).toMono().map { PaymentDTO(it) }
    }

    override fun getPayment(id: UUID, accountId: UUID): Mono<PaymentDTO> = Mono.defer {
        paymentRepository.findOneByIdAndAccountId(id, accountId)?.let {
            PaymentDTO(it)
        }?.toMono() ?: Mono.error(PaymentNotFoundException("Payment for id=$id does not exist!"))
    }

    override fun getPayments(accountId: UUID): Flux<PaymentDTO> {
        return paymentRepository.findAllByAccountId(accountId).map { PaymentDTO(it) }.toFlux()
    }

    @Transactional
    fun createWithOutbox(input: PaymentCommand, accountId: UUID): Payment {
        val payment = paymentRepository.save(input.withAccountId(accountId).payment)

        outboxService.append(topic, payment)
        return payment
    }
}

