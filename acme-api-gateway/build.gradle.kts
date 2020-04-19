import org.springframework.boot.gradle.tasks.bundling.BootJar

springBoot {
    mainClassName = "org.acme.financial.api.FinancialApiGatewayApplication"
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")
    implementation("org.springframework.cloud:spring-cloud-starter-circuitbreaker-reactor-resilience4j")
    implementation("org.springframework.boot:spring-boot-starter-data-redis-reactive")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.3.2")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:Hoxton.SR3")
    }
}

tasks {
    getByName<BootJar>("bootJar") {
        archiveBaseName.set("acme-api-gateway")
        archiveVersion.set("1.0.0")
    }
}