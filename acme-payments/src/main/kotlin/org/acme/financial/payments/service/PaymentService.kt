package org.acme.financial.payments.service

import org.acme.financial.payments.command.PaymentCommand
import org.acme.financial.payments.dto.PaymentDTO
import org.acme.financial.payments.dto.PaymentResultDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface PaymentService {
    fun create(input: PaymentCommand, accountId: UUID): Mono<PaymentDTO>

    fun getPayment(id: UUID, accountId: UUID): Mono<PaymentDTO>
    fun getPayments(accountId: UUID): Flux<PaymentDTO>

    fun processPaymentResult(result: PaymentResultDTO): Mono<Unit>
}