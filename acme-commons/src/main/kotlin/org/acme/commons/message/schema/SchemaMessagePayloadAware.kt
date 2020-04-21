package org.acme.commons.message.schema

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface SchemaMessagePayloadAware<Schema> {
    val schemaPayload: Schema
}