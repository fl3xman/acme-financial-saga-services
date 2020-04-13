package org.acme.financial.payments.command

import org.acme.commons.money.validator.annotation.Currency
import org.acme.financial.payments.domain.Payment
import org.javamoney.moneta.Money
import java.math.BigDecimal
import java.util.*
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Digits

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

// TODO: beneficiary

sealed class PaymentCommand(val payment: Payment) {

    // Fixme: decimal validations not working
    data class Transfer(
        @DecimalMin(value = "0.01")
        @Digits(integer = 9, fraction = 2)
        private val amount: BigDecimal,

        @Currency(units = ["EUR", "USD"])
        private val currency: String
    ) : PaymentCommand(
        Payment(UUID.randomUUID(), Money.of(amount, currency))
    )
}
