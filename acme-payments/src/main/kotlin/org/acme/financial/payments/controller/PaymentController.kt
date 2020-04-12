package org.acme.financial.payments.controller

import org.acme.financial.payments.command.PaymentCommand
import org.acme.financial.payments.dto.PaymentDTO
import org.acme.financial.payments.service.PaymentService
import org.springframework.beans.factory.annotation.Autowired
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
    @PostMapping
    fun create(@Validated @RequestBody input: PaymentCommand.Transfer): Mono<PaymentDTO> {
        return paymentService.create(input)
    }

    @GetMapping(path = [PaymentRoute.IDENTITY])
    fun getPayment(@PathVariable id: UUID): Mono<PaymentDTO> {
        return paymentService.getPayment(id)
    }

    @GetMapping
    fun getPayments(): Flux<PaymentDTO> {
        return paymentService.getPayments()
    }
}