package org.acme.financial.payments.domain

import java.util.*
import javax.persistence.Id
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Table

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
    val id: UUID
)