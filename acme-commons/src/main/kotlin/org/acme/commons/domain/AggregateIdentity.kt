package org.acme.commons.domain

import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface AggregateIdentity<ID> {
    val id: ID
}