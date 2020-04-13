package org.acme.financial.accounts

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@SpringBootApplication(
    scanBasePackages = [
        "org.acme.commons",
        "org.acme.financial.accounts"
    ]
)
class FinancialAccountApplication

fun main(args: Array<String>) {
    runApplication<FinancialAccountApplication>(*args)
}