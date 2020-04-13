package org.acme.financial.payments.command

import org.acme.commons.money.validator.annotation.Currency
import org.acme.commons.money.validator.annotation.IBAN
import org.acme.financial.payments.domain.Beneficiary
import org.acme.financial.payments.domain.Payment
import org.javamoney.moneta.Money
import java.math.BigDecimal
import java.security.Principal
import java.util.*
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Digits

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

sealed class PaymentCommand(
    val payment: Payment
) {
    // Fixme: decimal validations
    data class TransferUsingIBAN(

        @DecimalMin(value = "0.01")
        @Digits(integer = 9, fraction = 2)
        private val amount: BigDecimal,

        @Currency(units = ["EUR"])
        private val currency: String,

        @IBAN
        private val beneficiary: String

    ) : PaymentCommand(
        Payment(
            transaction = Money.of(amount, currency),
            beneficiary = Beneficiary(beneficiary)
        )
    )

    fun withPrincipal(principal: Principal): PaymentCommand = apply {
        payment.accountId = UUID.fromString(principal.name)
    }
}
