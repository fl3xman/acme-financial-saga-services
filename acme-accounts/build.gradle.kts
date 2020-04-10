springBoot {
    mainClassName = "org.acme.financial.accounts.FinancialAccountApplication"
    version = "1.0.0"
}

dependencies {
    implementation(project(":acme-commons"))
    implementation("org.liquibase:liquibase-core")

    runtimeOnly("com.h2database:h2:1.4.200")

    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}