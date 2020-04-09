package org.acme.financial.payments

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@SpringBootApplication(scanBasePackages = [
    "org.acme.commons",
    "org.acme.financial.payments"
])
class FinancialPaymentApplication

fun main(args: Array<String>) {
    runApplication<FinancialPaymentApplication>(*args)
}