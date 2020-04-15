package org.acme.financial.accounts.service

import reactor.core.publisher.Flux

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface AccountOperationService {
    fun onAccountOperationStarted(): Flux<Unit>
}