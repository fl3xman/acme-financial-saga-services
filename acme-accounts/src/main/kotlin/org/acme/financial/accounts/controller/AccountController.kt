package org.acme.financial.accounts.controller

import org.acme.commons.security.provideAccountIdentity
import org.acme.financial.accounts.dto.AccountBalanceDTO
import org.acme.financial.accounts.dto.AccountOperationDTO
import org.acme.financial.accounts.service.AccountOperationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@RestController
@RequestMapping(AccountRoute.ROOT)
class AccountController(
    @Autowired private val accountOperationService: AccountOperationService
) {
    @GetMapping(path = [AccountRoute.ME + AccountRoute.BALANCES])
    fun getAccountBalances(auth: Authentication): Flux<AccountBalanceDTO> {
        return accountOperationService.getAccountBalances(provideAccountIdentity(auth))
    }

    @GetMapping(path = [AccountRoute.ME + AccountRoute.HISTORY])
    fun getAccountHistory(auth: Authentication): Flux<AccountOperationDTO> {
        return accountOperationService.getAccountHistory(provideAccountIdentity(auth))
    }
}