springBoot {
    mainClassName = "org.acme.financial.payments.FinancialPaymentApplication"
    version = "1.0.0"
}

dependencies {
    implementation(project(":acme-commons"))
    implementation("org.liquibase:liquibase-core")

    implementation("org.jadira.usertype:usertype.core:7.0.0.CR1")

    runtimeOnly("com.h2database:h2:1.4.200")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}