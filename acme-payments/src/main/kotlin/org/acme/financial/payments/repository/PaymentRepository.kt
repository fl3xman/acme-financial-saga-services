package org.acme.financial.payments.repository

import org.acme.financial.payments.domain.Payment
import org.acme.financial.payments.domain.PaymentStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*


/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Repository
interface PaymentRepository : JpaRepository<Payment, UUID> {

    fun findOneByIdAndAccountId(id: UUID, accountId: UUID): Payment?
    fun findAllByAccountId(accountId: UUID): List<Payment>

    @Modifying
    @Query("update Payment p set p.status = ?2 where p.id = ?1")
    fun updateStatusById(id: UUID, status: PaymentStatus): Payment?
}