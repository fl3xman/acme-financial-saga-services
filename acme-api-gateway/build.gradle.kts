springBoot {
    mainClassName = "org.acme.financial.api.FinancialApiGatewayApplication"
    version = "1.0.0"
}

dependencies {
    implementation(project(":acme-commons"))
    implementation("org.springframework.cloud:spring-cloud-starter-gateway") {
        dependencyManagement {
            imports {
                mavenBom("org.springframework.cloud:spring-cloud-dependencies:Hoxton.SR3")
            }
        }
    }
}
