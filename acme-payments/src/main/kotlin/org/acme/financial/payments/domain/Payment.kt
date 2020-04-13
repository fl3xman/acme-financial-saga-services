package org.acme.financial.payments.domain

import org.acme.commons.domain.AggregateIdentity
import org.hibernate.annotations.Columns
import org.hibernate.annotations.Type
import org.javamoney.moneta.Money
import java.util.*
import javax.persistence.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Entity
@Table(name = "payment")
data class Payment(
    @Id
    @GeneratedValue(generator = "UUID")
    override val id: UUID,

    @Columns(
        columns = [
            Column(name = "transaction_currency"),
            Column(name = "transaction_amount")
        ]
    )
    @Type(type = "org.jadira.usertype.moneyandcurrency.moneta.PersistentMoneyAmountAndCurrency")
    val transaction: Money,
    val status: PaymentStatus = PaymentStatus.PENDING
) : AggregateIdentity<UUID>