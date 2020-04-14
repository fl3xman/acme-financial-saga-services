package org.acme.financial.payments.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.acme.commons.domain.AggregateIdentity
import org.hibernate.annotations.Columns
import org.hibernate.annotations.Type
import org.javamoney.moneta.Money
import java.time.LocalDateTime
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
    var id: UUID? = null,

    @Column(name = "account_id")
    var accountId: UUID? = null,

    @Columns(
        columns = [
            Column(name = "transaction_currency"),
            Column(name = "transaction_amount")
        ]
    )
    @Type(type = "org.jadira.usertype.moneyandcurrency.moneta.PersistentMoneyAmountAndCurrency")
    val transaction: Money,

    @Embedded
    @AttributeOverrides(
        value = [
            AttributeOverride(name = "value", column = Column(name = "beneficiary_value")),
            AttributeOverride(name = "type", column = Column(name = "beneficiary_type"))
        ]
    )
    val beneficiary: Beneficiary,
    val status: PaymentStatus = PaymentStatus.PENDING,

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "modified_at")
    val modifiedAt: LocalDateTime

) : AggregateIdentity<UUID> {
    @get:JsonIgnore
    override val aggregateId: UUID
        get() = accountId!!
}