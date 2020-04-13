package org.acme.financial.payments.configuration

import org.h2.tools.Server
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Component
class ConsoleConfiguration {

    private var web: Server? = null
    private var tcp: Server? = null

    @EventListener(ContextRefreshedEvent::class)
    fun start() {
        web = Server.createWebServer("-webPort", "8082", "-tcpAllowOthers").start()
        tcp = Server.createWebServer("-tcpPort", "9092", "-tcpAllowOthers").start()
    }

    @EventListener(ContextClosedEvent::class)
    fun stop() {
        web?.stop()
        tcp?.stop()
    }
}