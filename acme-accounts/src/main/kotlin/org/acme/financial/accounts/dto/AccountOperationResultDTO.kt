package org.acme.financial.accounts.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import org.acme.commons.domain.AggregateIdentity
import org.acme.financial.accounts.bo.AccountSinglePaymentBO
import org.acme.financial.accounts.domain.AccountOperationReason
import org.acme.financial.accounts.domain.AccountOperationStatus
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class AccountOperationResultDTO(
    val id: UUID,
    @JsonIgnore
    override val aggregateId: UUID,
    val status: AccountOperationStatus,
    val reason: AccountOperationReason? = null
): AggregateIdentity<UUID> {
    constructor(
        payment: AccountSinglePaymentBO,
        status: AccountOperationStatus,
        reason: AccountOperationReason? = null
    ): this(
        payment.id, payment.accountId, status, reason
    )
}