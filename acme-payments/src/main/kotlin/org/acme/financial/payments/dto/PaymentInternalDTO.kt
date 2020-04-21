package org.acme.financial.payments.dto

import org.acme.commons.money.beneficiary.Beneficiary
import org.acme.financial.payments.domain.PaymentStatus

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class PaymentInternalDTO(
    val id: String,
    val accountId: String,
    val transaction: TransactionInternalDTO,
    val status: PaymentStatus,
    val beneficiary: Beneficiary
)