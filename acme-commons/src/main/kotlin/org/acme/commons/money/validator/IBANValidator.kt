package org.acme.commons.money.validator

import org.acme.commons.logging.provideLogger
import org.acme.commons.money.validator.annotation.IBAN
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
class IBANValidator : ConstraintValidator<IBAN, String> {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    override fun initialize(parameters: IBAN?) {}

    override fun isValid(value: String?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        return value?.let {
            Regex("[a-zA-Z]{2}[0-9]{2}[a-zA-Z0-9]{4}[0-9]{7}([a-zA-Z0-9]?){0,16}").matches(it)
        } ?: false
    }
}