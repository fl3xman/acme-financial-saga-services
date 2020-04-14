package org.acme.commons.security

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

class AccountIdentityAwareUser<ID>(override val accountId: ID, user: UserDetails) : User(
    user.username, user.password, user.authorities
), AccountIdentityAware<ID>
