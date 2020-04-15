package org.acme.financial.accounts.event

import org.acme.commons.logging.provideLogger
import org.acme.financial.accounts.service.AccountOperationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Component
class AccountOperationEventProcessor(
    @Autowired private val accountOperationService: AccountOperationService
) {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    @EventListener(ApplicationReadyEvent::class)
    fun onReady() {
        accountOperationService.onAccountOperationStarted().subscribe()
    }
}