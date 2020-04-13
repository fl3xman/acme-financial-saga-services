package org.acme.commons.money.validator.annotation

import org.acme.commons.money.validator.IBANValidator
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
@Constraint(validatedBy = [IBANValidator::class])
annotation class IBAN(
    val message: String = "{javax.validation.constraints.Pattern.message}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)