package org.acme.financial.accounts.repository

import org.acme.financial.accounts.domain.AccountOperation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Repository
interface AccountOperationRepository: JpaRepository<AccountOperation, UUID> {

    @Query("select sum(o.transaction.amount) from AccountOperation o where o.accountId = ?1 and o.transaction.currency = ?2")
    fun getBalanceByAccountIdAndCurrency(accountId: UUID, currency: String): BigDecimal?

    fun findOneByIdAndAccountId(id: UUID, accountId: UUID): AccountOperation?
    fun findAllByAccountId(accountId: UUID): List<AccountOperation>
}