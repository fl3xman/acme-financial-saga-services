package org.acme.financial.accounts.dto

import org.acme.commons.money.beneficiary.Beneficiary
import org.acme.financial.accounts.domain.AccountOperation
import java.math.BigDecimal
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class AccountOperationDTO(
    val id: UUID,
    val amount: BigDecimal,
    val currency: String,
    val beneficiary: Beneficiary
) {
    constructor(model: AccountOperation): this(
        model.id!!,
        model.transaction.numberStripped,
        model.transaction.currency.currencyCode,
        model.beneficiary
    )
}