package org.acme.financial.api.openapi.configuration

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme

import org.springframework.context.annotation.Configuration


/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */
@SecurityScheme(
    name = "Basic scheme",
    type = SecuritySchemeType.HTTP,
    scheme = "basic",
    `in` = SecuritySchemeIn.HEADER
)
@Configuration
class OpenapiConfiguration