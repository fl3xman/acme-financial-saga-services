plugins {

    id("org.springframework.boot") version "2.2.6.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("com.commercehub.gradle.plugin.avro") version "0.19.1"

    kotlin("jvm") version "1.3.71"
    kotlin("plugin.noarg") version "1.3.71"
    kotlin("plugin.spring") version "1.3.71"
    kotlin("plugin.allopen") version "1.3.71"
    kotlin("plugin.jpa") version "1.3.71"
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("http://packages.confluent.io/maven") }
}

subprojects {

    group = "org.acme"

    apply(plugin = "org.jetbrains.kotlin.plugin.noarg")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "com.commercehub.gradle.plugin.avro")

    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("http://packages.confluent.io/maven") }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    configurations {

        api {
            resolutionStrategy.failOnVersionConflict()
        }

        implementation {
            resolutionStrategy.failOnVersionConflict()
        }
    }


    tasks {

        compileKotlin {
            kotlinOptions {
                jvmTarget = "${JavaVersion.VERSION_11}"
                freeCompilerArgs = listOf("-Xjsr305=strict")
                javaParameters = true
            }
        }

        compileTestKotlin {
            kotlinOptions {
                jvmTarget = "${JavaVersion.VERSION_11}"
            }
        }
    }

}