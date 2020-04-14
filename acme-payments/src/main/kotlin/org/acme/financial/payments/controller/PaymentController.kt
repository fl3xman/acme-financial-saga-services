package org.acme.financial.payments.controller

import org.acme.commons.security.provideAccountIdentity
import org.acme.financial.payments.command.PaymentCommand
import org.acme.financial.payments.dto.PaymentDTO
import org.acme.financial.payments.service.PaymentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@RestController
@RequestMapping(PaymentRoute.ROOT)
class PaymentController(
    @Autowired private val paymentService: PaymentService
) {
    @PostMapping(path = [PaymentRoute.TRANSFER_IBAN])
    fun transferUsingIBAN(
        @Validated @RequestBody input: PaymentCommand.TransferUsingIBAN, auth: Authentication
    ): Mono<PaymentDTO> {
        return paymentService.create(input, provideAccountIdentity(auth))
    }

    @GetMapping(path = [PaymentRoute.IDENTITY])
    fun getPayment(@PathVariable id: UUID, auth: Authentication): Mono<PaymentDTO> {
        return paymentService.getPayment(id, provideAccountIdentity(auth))
    }

    @GetMapping
    fun getPayments(auth: Authentication): Flux<PaymentDTO> {
        return paymentService.getPayments(provideAccountIdentity(auth))
    }
}