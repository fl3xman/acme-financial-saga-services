package org.acme.commons.domain

import com.fasterxml.jackson.annotation.JsonIgnore

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

interface AggregateIdentity<ID> {
    @get:JsonIgnore
    val aggregateId: ID
}