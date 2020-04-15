package org.acme.financial.accounts.dto

import org.acme.commons.money.beneficiary.Beneficiary
import org.acme.financial.accounts.domain.Account
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class AccountDTO(
    val id: UUID,
    val beneficiary: Beneficiary
) {
    constructor(model: Account) : this(
        model.id!!,
        model.beneficiary
    )
}