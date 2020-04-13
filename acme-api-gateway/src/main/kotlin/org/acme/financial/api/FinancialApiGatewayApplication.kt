package org.acme.financial.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@SpringBootApplication(
    scanBasePackages = [
        "org.acme.financial.api"
    ]
)
class FinancialApiGatewayApplication

fun main(args: Array<String>) {
    runApplication<FinancialApiGatewayApplication>(*args)
}