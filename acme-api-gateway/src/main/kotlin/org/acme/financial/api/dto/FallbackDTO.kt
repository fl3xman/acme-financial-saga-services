package org.acme.financial.api.dto

import org.springframework.http.HttpStatus

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

data class FallbackDTO(val reason: String = HttpStatus.SERVICE_UNAVAILABLE.name)