package org.acme.financial.accounts.domain

import com.fasterxml.jackson.annotation.JsonValue

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
enum class AccountTransactionType(@JsonValue val value: String) {
    SINGLE_PAYMENT("single_payment"),
    BULK_PAYMENT("bulk_payment"),
    LOCAL_DEPOSIT("local_deposit")
}