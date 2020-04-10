package org.acme.financial.accounts.domain

import org.springframework.data.annotation.Id
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Table

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
    val id: UUID
)