package org.acme.financial.accounts.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import org.acme.commons.domain.AggregateIdentity
import org.acme.financial.accounts.bo.AccountOperationExchangeBO
import org.acme.financial.accounts.domain.AccountOperationStatus
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class AccountOperationExchangeDTO(
    val id: UUID,
    @JsonIgnore
    override val aggregateId: UUID,
    val status: AccountOperationStatus,
    val reason: String? = null
): AggregateIdentity<UUID> {
    constructor(exchange: AccountOperationExchangeBO, status: AccountOperationStatus): this(
        exchange.id, exchange.accountId, status
    )
}