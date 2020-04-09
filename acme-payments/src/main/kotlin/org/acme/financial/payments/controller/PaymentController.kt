package org.acme.financial.payments.controller

import org.acme.financial.payments.dto.PaymentDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@RestController
@RequestMapping(PaymentRoute.ROOT)
class PaymentController {

    @GetMapping
    fun getPayments(): Flux<PaymentDTO> = Flux.fromIterable(listOf(PaymentDTO(10L)))
}