package org.acme.financial.accounts.dto

import org.javamoney.moneta.Money
import java.math.BigDecimal
import javax.persistence.Tuple

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class AccountBalanceDTO(
    val amount: BigDecimal,
    val currency: String
) {
    constructor(balance: Money) : this(
        balance.numberStripped,
        balance.currency.currencyCode
    )

    constructor(balance: Tuple) : this(
        balance.get(0, BigDecimal::class.java),
        balance.get(1, String::class.java)
    )
}