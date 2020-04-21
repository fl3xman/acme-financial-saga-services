package org.acme.financial.payments.dto

import java.math.BigDecimal

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class TransactionInternalDTO(
    val amount: BigDecimal,
    val currency: String
)