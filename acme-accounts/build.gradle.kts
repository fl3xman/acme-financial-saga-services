import org.springframework.boot.gradle.tasks.bundling.BootJar

springBoot {
    mainClassName = "org.acme.financial.accounts.FinancialAccountApplication"
}

dependencies {
    implementation(project(":acme-commons"))
    implementation("org.liquibase:liquibase-core:3.8.9")

    implementation("org.jadira.usertype:usertype.core:7.0.0.CR1")

    runtimeOnly("com.h2database:h2:1.4.200")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks {
    getByName<BootJar>("bootJar") {
        archiveBaseName.set("acme-accounts")
        archiveVersion.set("1.0.0")
    }
}