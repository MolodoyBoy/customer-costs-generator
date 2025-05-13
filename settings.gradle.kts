pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    val springBootVersion: String by settings

    plugins {
        id("java")
        id("com.google.cloud.tools.jib") version ("3.4.1")
        id("io.spring.dependency-management") version ("1.1.7")
        id("org.springframework.boot") version (springBootVersion)
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            library("springContext", "org.springframework", "spring-context").withoutVersion()
            library("springAutoConfigure", "org.springframework", "spring-boot-autoconfigure").withoutVersion()
            library("springBootStarter", "org.springframework.boot", "spring-boot-starter").withoutVersion()
            library("springKafka", "org.springframework.kafka", "spring-kafka").withoutVersion()
        }
    }
}

rootProject.name = "customer-costs-generator"

