package org.acme.commons.message

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

interface MessageTopicAware {
    val topic: String
}