package org.acme.financial.accounts.service

import org.acme.financial.accounts.event.AccountOperationEvent
import reactor.core.publisher.Mono

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface AccountOperationService {
    fun processAccountOperationStartedEvent(event: AccountOperationEvent): Mono<Unit>
}