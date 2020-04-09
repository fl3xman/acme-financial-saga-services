package org.acme.financial.accounts.controller

import org.acme.financial.accounts.dto.AccountDTO
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
@RequestMapping(AccountRoute.ROOT)
class AccountController {

    @GetMapping
    fun getAccounts(): Flux<AccountDTO> = Flux.fromIterable(listOf(AccountDTO("John")))
}