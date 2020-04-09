package org.acme.financial.accounts

import org.acme.commons.logging.provideLogger
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@SpringBootApplication(scanBasePackages = [
    "org.acme.commons",
    "org.acme.financial.accounts"
])
class FinancialAccountApplication {
    companion object {

        @JvmStatic
        private val logger = provideLogger()
    }

    init {
        logger.info("Account service initialized!")
    }
}

fun main(args: Array<String>) {
    runApplication<FinancialAccountApplication>(*args)
}