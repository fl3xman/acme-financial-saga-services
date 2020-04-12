package org.acme.commons.money.validator

import org.acme.commons.logging.provideLogger
import org.acme.commons.money.validator.annotation.Currency
import javax.money.CurrencyUnit
import javax.money.Monetary
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

class CurrencyValidator: ConstraintValidator<Currency, String> {

    companion object {
        @JvmStatic
        private val logger = provideLogger()
    }

    private var currencies: Set<CurrencyUnit> = emptySet()

    override fun initialize(parameters: Currency?) {
        parameters?.let {
            currencies = it.units.map { u -> Monetary.getCurrency(u) }.toSet()
        }
    }

    override fun isValid(value: String?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        return value?.let {
            try {
                return currencies.contains(Monetary.getCurrency(it))
            } catch (exception: Throwable) {
                logger.debug("Value: $value validation failed with error: $exception")
                false
            }
        } ?: false
    }
}