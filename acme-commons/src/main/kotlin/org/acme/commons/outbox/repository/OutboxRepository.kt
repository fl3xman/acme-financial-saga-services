package org.acme.commons.outbox.repository

import org.acme.commons.outbox.domain.Outbox
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Repository
interface OutboxRepository : JpaRepository<Outbox, UUID> {
}