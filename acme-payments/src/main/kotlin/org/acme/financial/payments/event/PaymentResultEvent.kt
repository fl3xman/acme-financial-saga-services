package org.acme.financial.payments.event

import org.acme.financial.payments.domain.PaymentStatus
import java.util.*

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class PaymentResultEvent(val id: UUID, val status: PaymentStatus)