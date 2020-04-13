package org.acme.financial.payments.domain

import javax.persistence.Embeddable

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Embeddable
data class Beneficiary(
    val value: String,
    val type: BeneficiaryType = BeneficiaryType.IBAN
)