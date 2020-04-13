package org.acme.financial.payments.service

import org.acme.financial.payments.command.PaymentCommand
import org.acme.financial.payments.dto.PaymentDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.security.Principal
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface PaymentService {
    fun create(input: PaymentCommand, principal: Principal): Mono<PaymentDTO>

    fun getPayment(id: UUID, principal: Principal): Mono<PaymentDTO>
    fun getPayments(principal: Principal): Flux<PaymentDTO>
}