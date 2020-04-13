package org.acme.financial.payments.command

import org.acme.commons.outbox.command.OutboxCommand
import org.acme.financial.payments.domain.Payment

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

sealed class PaymentOutboxCommand(
    override val topic: String,
    override val event: String,
    override val payload: Payment
) : OutboxCommand<Payment> {
    class Create(payment: Payment) : PaymentOutboxCommand("", "", payment)
}