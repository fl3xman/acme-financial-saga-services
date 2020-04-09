springBoot {
    mainClassName = "org.acme.financial.payments.FinancialPaymentApplication"
    version = "1.0.0"
}

dependencies {
    implementation(project(":acme-commons"))

    implementation("org.springframework.data:spring-data-r2dbc:1.1.0.RC1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}