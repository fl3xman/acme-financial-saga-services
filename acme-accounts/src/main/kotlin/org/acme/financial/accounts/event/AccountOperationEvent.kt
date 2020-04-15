package org.acme.financial.accounts.event

import org.acme.commons.money.beneficiary.Beneficiary
import org.acme.financial.accounts.domain.AccountOperationStatus
import org.javamoney.moneta.Money
import java.time.LocalDateTime
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class AccountOperationEvent(
    val id: UUID,
    val accountId: UUID,
    // val transaction: Money,
    val beneficiary: Beneficiary,
    val status: AccountOperationStatus,
    val createdAt: LocalDateTime
)