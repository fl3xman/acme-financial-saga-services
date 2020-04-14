package org.acme.commons.security

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

interface AccountIdentityAware<ID> {
    val accountId: ID
}

