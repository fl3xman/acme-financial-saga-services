import org.springframework.boot.gradle.tasks.bundling.BootJar

tasks {
    getByName<Jar>("jar") {
        enabled = true
    }

    getByName<BootJar>("bootJar") {
        enabled = false
    }
}

dependencies {
    // Align versions of all Kotlin components
    api(platform("org.jetbrains.kotlin:kotlin-bom"))

    api ("org.jetbrains.kotlin:kotlin-reflect")
    api ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    api("org.springframework.boot:spring-boot-starter-webflux")
    api("org.springframework.boot:spring-boot-starter-actuator")
}