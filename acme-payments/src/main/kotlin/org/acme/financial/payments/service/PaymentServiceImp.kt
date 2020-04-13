package org.acme.financial.payments.service

import org.acme.commons.outbox.service.OutboxService
import org.acme.financial.payments.command.PaymentCommand
import org.acme.financial.payments.domain.Payment
import org.acme.financial.payments.dto.PaymentDTO
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
import java.security.Principal
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

    override fun create(input: PaymentCommand, principal: Principal): Mono<PaymentDTO> = Mono.defer {
        createWithOutbox(input, principal).toMono().map { PaymentDTO(it) }
    }

    override fun getPayment(id: UUID, principal: Principal): Mono<PaymentDTO> = Mono.defer {
        paymentRepository.findByIdOrNull(id)?.let {
            PaymentDTO(it)
        }?.toMono() ?: Mono.error(EntityNotFoundException())
    }

    override fun getPayments(principal: Principal): Flux<PaymentDTO> {
        return paymentRepository.findAll().toFlux().map { PaymentDTO(it) }
    }

    @Transactional
    fun createWithOutbox(input: PaymentCommand, principal: Principal): Payment {
        val payment = paymentRepository.save(input.withPrincipal(principal).payment)

        outboxService.append(topic, payment)
        return payment
    }
}

