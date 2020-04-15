package org.acme.financial.accounts.domain

import org.acme.commons.money.beneficiary.Beneficiary
import java.util.*
import javax.persistence.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Entity
@Table(name = "account")
data class Account(
    @Id
    @GeneratedValue(generator = "UUID")
    val id: UUID,

    @Embedded
    @AttributeOverrides(
        value = [
            AttributeOverride(name = "value", column = Column(name = "beneficiary_value")),
            AttributeOverride(name = "type", column = Column(name = "beneficiary_type"))
        ]
    )
    val beneficiary: Beneficiary,

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    var operations: List<AccountOperation> = emptyList()
)