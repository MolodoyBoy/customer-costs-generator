plugins {
    id("java")
    id("org.springframework.boot")
    id("com.google.cloud.tools.jib")
}

tasks.jar {
    enabled = false
}

springBoot {
    buildInfo()
}

group = "com.oleg.customer.costs"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
        vendor = JvmVendorSpec.ADOPTIUM
    }
}

val springBootVersion: String by project

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:${springBootVersion}"))
    implementation(libs.springKafka)
    implementation(libs.springContext)
    implementation(libs.springBootStarter)
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}

val dockerHubUsername: String by project
val dockerHubPassword = System.getenv("DOCKER_HUB_PASSWORD")
val imageVersion = System.getenv().getOrDefault("IMAGE_VERSION", "")

jib {
    from {
        image = "eclipse-temurin:21-jdk"
    }

    to {
        image = "molodoyboy777/customer-costs-generator:${imageVersion}"

        auth {
            username = dockerHubUsername
            password = dockerHubPassword
        }
    }

    container {
        jvmFlags = listOf("-XX:MaxRAMPercentage=80")
        mainClass = "com.oleg.customer.costs.CustomerCostsGenerationApplication"
    }
}