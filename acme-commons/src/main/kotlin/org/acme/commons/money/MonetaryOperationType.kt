package org.acme.commons.money

import com.fasterxml.jackson.annotation.JsonValue

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

enum class MonetaryOperationType(@JsonValue val value: String) {
    DEPOSIT("deposit"),
    WITHDRAW("withdraw")
}