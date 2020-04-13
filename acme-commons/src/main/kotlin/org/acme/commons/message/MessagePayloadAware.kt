package org.acme.commons.message

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
interface MessagePayloadAware {
    val payload: String
}