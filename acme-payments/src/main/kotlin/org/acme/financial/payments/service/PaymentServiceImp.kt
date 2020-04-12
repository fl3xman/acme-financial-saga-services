package org.acme.financial.payments.service

import org.acme.commons.outbox.service.OutboxService
import org.acme.financial.payments.command.PaymentCommand
import org.acme.financial.payments.command.PaymentOutboxCommand
import org.acme.financial.payments.domain.Payment
import org.acme.financial.payments.dto.PaymentDTO
import org.acme.financial.payments.repository.PaymentRepository
import org.springframework.beans.factory.annotation.Autowired
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
    @Autowired private val outboxService: OutboxService
): PaymentService {

    override fun create(input: PaymentCommand): Mono<PaymentDTO> = Mono.defer {
        execute(input).toMono().map { PaymentDTO(it) }
    }

    override fun getPayment(id: UUID): Mono<PaymentDTO> = Mono.defer {
        paymentRepository.findById(id).toMono().map {
            PaymentDTO(it.orElseThrow {
                    throw EntityNotFoundException()
                })
        }
    }

    override fun getPayments(): Flux<PaymentDTO> {
        return paymentRepository.findAll().toFlux().map { PaymentDTO(it) }
    }

    @Transactional
    private fun execute(input: PaymentCommand): Payment {
        return paymentRepository.save(input.payment).also {
            outboxService.execute(PaymentOutboxCommand.Create(it))
        }
    }
}