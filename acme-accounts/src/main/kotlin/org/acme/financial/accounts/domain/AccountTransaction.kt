package org.acme.financial.accounts.domain

import org.hibernate.annotations.Columns
import org.hibernate.annotations.Type
import org.javamoney.moneta.Money
import java.math.BigDecimal
import java.util.*
import javax.persistence.Column
import javax.persistence.Embeddable
import javax.persistence.EnumType
import javax.persistence.Enumerated

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Embeddable
data class AccountTransaction(

    @Column(name = "transaction_id")
    val id: UUID,

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    val type: AccountTransactionType,

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