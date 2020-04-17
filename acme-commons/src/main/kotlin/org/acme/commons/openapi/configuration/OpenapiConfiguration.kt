package org.acme.commons.openapi.configuration

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean

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
class OpenapiConfiguration {
    @Bean
    fun openAPI(): OpenAPI = OpenAPI().addServersItem(Server().url("/"))
}