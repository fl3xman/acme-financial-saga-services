package org.acme.commons.event

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

interface ReadyEventHandler {
    fun onReady()
}