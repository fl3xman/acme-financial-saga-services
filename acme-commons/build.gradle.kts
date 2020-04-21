import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks {
    getByName<Jar>("jar") {
        enabled = true
    }

    getByName<BootJar>("bootJar") {
        enabled = false
    }
}

avro {

}

dependencies {
    // Align versions of all Kotlin components
    api(platform("org.jetbrains.kotlin:kotlin-bom"))

    api("org.jetbrains.kotlin:kotlin-reflect")
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    api("javax.money:money-api:1.0.3")
    api("org.javamoney:moneta:1.3")
    api("org.zalando:jackson-datatype-money:1.1.1")

    api("com.fasterxml.jackson.module:jackson-module-kotlin")
    api("io.projectreactor.kotlin:reactor-kotlin-extensions")
    api("io.projectreactor.kafka:reactor-kafka:1.2.2.RELEASE")

    api("org.springframework.kafka:spring-kafka:2.4.5.RELEASE")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-jdbc")
    api("org.springframework.boot:spring-boot-starter-webflux")
    api("org.springframework.boot:spring-boot-starter-security")
    api("org.springframework.boot:spring-boot-starter-actuator")

    api("org.apache.avro:avro:1.9.2")
    api("io.confluent:kafka-avro-serializer:5.4.1")

    api("org.springdoc:springdoc-openapi-webflux-core:1.3.2")
    api("org.springdoc:springdoc-openapi-kotlin:1.3.2")
}