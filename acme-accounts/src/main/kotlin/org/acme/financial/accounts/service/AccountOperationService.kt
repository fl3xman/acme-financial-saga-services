package org.acme.financial.accounts.service

import org.acme.financial.accounts.dto.AccountOperationDTO
import org.acme.financial.accounts.bo.AccountOperationExchangeBO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface AccountOperationService {

    fun getAccountOperation(id: UUID, accountId: UUID): Mono<AccountOperationDTO>
    fun getAccountOperations(accountId: UUID): Flux<AccountOperationDTO>

    fun processAccountOperationStartedEvent(exchange: AccountOperationExchangeBO): Mono<Unit>
}