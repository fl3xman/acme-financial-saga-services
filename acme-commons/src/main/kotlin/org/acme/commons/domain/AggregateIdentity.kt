package org.acme.commons.domain

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

interface AggregateIdentity<ID> {
    val aggregateId: ID
}