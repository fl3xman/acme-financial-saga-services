rootProject.name = "acme-payment-saga-services"

include("acme-commons")
include("acme-accounts")
include("acme-payments")
include("acme-api-gateway")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://dl.bintray.com/gradle/gradle-plugins") }
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "org.springframework.boot") {
                useModule("org.springframework.boot:spring-boot-gradle-plugin:${requested.version}")
            }
        }
    }
}