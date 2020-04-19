package org.acme.financial.accounts.domain

import com.fasterxml.jackson.annotation.JsonValue

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
enum class AccountTransactionType(@JsonValue val value: String) {
    SINGLE_PAYMENT("SINGLE_PAYMENT"),
    BULK_PAYMENT("BULK_PAYMENT"),
    LOCAL_DEPOSIT("LOCAL_DEPOSIT")
}