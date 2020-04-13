package org.acme.financial.payments.repository

import org.acme.financial.payments.domain.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Repository
interface PaymentRepository : JpaRepository<Payment, UUID> {
}