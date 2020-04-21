package org.acme.commons.message.schema

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface SchemaPayloadAware<Schema> {
    val schemaPayload: Schema
}