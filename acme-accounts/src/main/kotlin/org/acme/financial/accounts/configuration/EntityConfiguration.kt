package org.acme.financial.accounts.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 *
 * @project acme-payment-saga-services
 * @author fl3xman
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = [
        "org.acme.commons",
        "org.acme.financial.accounts"
    ]
)
@EntityScan(
    basePackages = [
        "org.acme.commons",
        "org.acme.financial.accounts"
    ]
)
class EntityConfiguration