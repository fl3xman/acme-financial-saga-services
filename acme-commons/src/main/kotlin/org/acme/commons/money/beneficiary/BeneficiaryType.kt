package org.acme.commons.money.beneficiary

import com.fasterxml.jackson.annotation.JsonValue

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
enum class BeneficiaryType(@JsonValue val value: String) {
    IBAN("IBAN")
}