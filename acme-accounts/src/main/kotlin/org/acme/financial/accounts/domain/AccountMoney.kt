package org.acme.financial.accounts.domain

import org.hibernate.annotations.Columns
import org.hibernate.annotations.Type
import org.javamoney.moneta.Money
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Embeddable

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Embeddable
data class AccountMoney(

    @Columns(
        columns = [
            Column(name = "transaction_currency"),
            Column(name = "transaction_amount")
        ]
    )
    @Type(type = "org.jadira.usertype.moneyandcurrency.moneta.PersistentMoneyAmountAndCurrency")
    val composite: Money,

    @Column(name = "transaction_amount", insertable = false, updatable = false)
    val amount: BigDecimal? = null,

    @Column(name = "transaction_currency", insertable = false, updatable = false)
    val currency: String? = null
)