package org.acme.commons.security

import org.springframework.security.core.Authentication

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

fun <ID> provideAccountIdentity(authentication: Authentication): ID =
    (authentication.principal as AccountIdentityAware<ID>).accountId