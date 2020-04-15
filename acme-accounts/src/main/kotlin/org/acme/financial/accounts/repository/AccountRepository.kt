package org.acme.financial.accounts.repository

import org.acme.financial.accounts.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Repository
interface AccountRepository: JpaRepository<Account, UUID> {
}