package org.acme.financial.payments.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories(
    basePackages = [
        "org.acme.commons",
        "org.acme.financial.payments"
    ]
)
@EntityScan(
    basePackages = [
        "org.acme.commons",
        "org.acme.financial.payments"
    ]
)
class EntityConfiguration