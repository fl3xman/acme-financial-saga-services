package org.acme.commons.outbox.repository

import org.acme.commons.outbox.domain.Outbox
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface OutboxRepository: JpaRepository<Outbox, UUID> {
}