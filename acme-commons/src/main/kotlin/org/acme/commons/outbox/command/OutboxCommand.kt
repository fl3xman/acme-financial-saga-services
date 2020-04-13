package org.acme.commons.outbox.command

import org.acme.commons.domain.AggregateIdentity
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

interface OutboxCommand<T : AggregateIdentity<UUID>> {
    val topic: String
    val payload: T
}