package org.acme.financial.payments.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class PaymentNotFoundException(message: String): RuntimeException(message)