@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.0"
    kotlin("plugin.spring") version "1.6.0"
}

group = "com.ecommerce"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

apply(plugin = "jacoco")

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:2.6.4")
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.6.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.4")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.5")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.6")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.6.6")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.0-native-mt")
    implementation("io.r2dbc:r2dbc-postgresql:0.8.10.RELEASE")
    implementation("org.liquibase:liquibase-core:4.8.0")
    runtimeOnly("io.r2dbc:r2dbc-postgresql:0.8.10.RELEASE")
    runtimeOnly("org.postgresql:postgresql:42.3.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.4")
    testImplementation("io.projectreactor:reactor-test:3.4.15")
    testImplementation("io.mockk:mockk:1.12.3")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation("org.testcontainers:testcontainers:1.16.3")
    testImplementation("org.testcontainers:junit-jupiter:1.16.3")
    testImplementation("org.testcontainers:postgresql:1.16.3")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<JacocoReport> {
    reports {
        xml.isEnabled = true
        html.isEnabled = false
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
