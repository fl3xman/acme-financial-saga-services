package org.acme.commons.openapi

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

object OpenapiRoute {
    const val DOCS = "/docs/**"
    const val SWAGGER_UI = "/webjars/swagger-ui/**"
    const val SWAGGER_API = "/v3/api-docs/**"

    val ANY = arrayOf(DOCS, SWAGGER_UI, SWAGGER_API)
}