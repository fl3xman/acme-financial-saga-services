package org.acme.commons.money.beneficiary

import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Embeddable
data class Beneficiary(
    val value: String,

    @Enumerated(EnumType.STRING)
    val type: BeneficiaryType = BeneficiaryType.IBAN
)