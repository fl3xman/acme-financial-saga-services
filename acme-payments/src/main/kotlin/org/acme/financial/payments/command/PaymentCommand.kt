package org.acme.financial.payments.command

import org.acme.financial.payments.domain.Payment
import org.javamoney.moneta.Money
import java.math.BigDecimal
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

// TODO: beneficiary

sealed class PaymentCommand(val payment: Payment) {
    class Transfer(amount: BigDecimal, currency: String): PaymentCommand(
        Payment(UUID.randomUUID(), Money.of(amount, currency))
    )
}
