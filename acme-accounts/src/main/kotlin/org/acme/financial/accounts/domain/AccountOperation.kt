package org.acme.financial.accounts.domain

import org.acme.commons.money.MonetaryOperationType
import org.acme.commons.money.beneficiary.Beneficiary
import org.hibernate.annotations.Columns
import org.hibernate.annotations.Type
import org.javamoney.moneta.Money
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Entity
@Table(name = "account_operation")
@EntityListeners(AuditingEntityListener::class)
data class AccountOperation(
    @Id
    @GeneratedValue(generator = "UUID")
    var id: UUID? = null,

    @Column(name = "account_id", insertable = false, updatable = false)
    var accountId: UUID? = null,

    /*
    @Embedded
    val transaction: AggregateMoney,
    */
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

    @Enumerated(EnumType.STRING)
    val type: MonetaryOperationType,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH])
    @JoinColumn(name="account_id")
    var account: Account? = null,

    @CreatedDate
    @Column(name = "created_at")
    var createdAt: LocalDateTime? = null
)