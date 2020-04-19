package org.acme.financial.api.controller

import org.acme.financial.api.dto.FallbackDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@RestController
@RequestMapping(FallbackRoute.ROOT)
class FallbackController {

    @GetMapping
    fun getFallback(): Mono<FallbackDTO> = Mono.just(FallbackDTO())
}