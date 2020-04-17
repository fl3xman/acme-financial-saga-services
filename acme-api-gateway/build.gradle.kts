springBoot {
    mainClassName = "org.acme.financial.api.FinancialApiGatewayApplication"
    version = "1.0.0"
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.cloud:spring-cloud-starter-gateway")

    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.3.2")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:Hoxton.SR3")
    }
}
