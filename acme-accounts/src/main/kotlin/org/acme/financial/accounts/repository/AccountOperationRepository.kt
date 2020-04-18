package org.acme.financial.accounts.repository

import org.acme.financial.accounts.domain.AccountOperation
import org.acme.financial.accounts.domain.AccountTransactionType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.*
import javax.persistence.Tuple

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Repository
interface AccountOperationRepository: JpaRepository<AccountOperation, UUID> {

    @Query("select sum(o.transaction.amount) from AccountOperation o where o.accountId = ?1 and o.transaction.currency = ?2")
    fun getBalanceByAccountIdAndCurrency(accountId: UUID, currency: String): BigDecimal?

    @Query("select sum(o.transaction.amount), o.transaction.currency from AccountOperation o where o.accountId = ?1 group by o.transaction.currency")
    fun getAllBalancesByAccountId(accountId: UUID): List<Tuple>

    fun countByTransactionIdAndTransactionType(transactionId: UUID, transactionType: AccountTransactionType): Long
    fun findAllByAccountId(accountId: UUID): List<AccountOperation>
}