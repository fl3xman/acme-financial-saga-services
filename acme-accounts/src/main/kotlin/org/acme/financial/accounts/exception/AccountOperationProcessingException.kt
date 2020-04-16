package org.acme.financial.accounts.exception

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

class AccountOperationProcessingException(message: String): RuntimeException(message) {
    constructor() : this("")
}