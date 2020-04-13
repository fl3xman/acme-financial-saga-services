package org.acme.commons.money.validator.annotation

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

import org.acme.commons.money.validator.CurrencyValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [CurrencyValidator::class])
annotation class Currency(
    val message: String = "{javax.validation.constraints.Pattern.message}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val units: Array<String> = []
)