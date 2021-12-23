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

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:2.5.6")
	implementation("org.springframework.boot:spring-boot-starter-webflux:2.6.1")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.1")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.5")
	implementation("org.springdoc:springdoc-openapi-kotlin:1.5.12")
	implementation("org.springdoc:springdoc-openapi-webflux-ui:1.5.12")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.5.2-native-mt")
	implementation("io.r2dbc:r2dbc-postgresql:0.8.10.RELEASE")
	runtimeOnly("io.r2dbc:r2dbc-postgresql:0.8.10.RELEASE")
	runtimeOnly("org.postgresql:postgresql:42.3.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.1")
	testImplementation("io.projectreactor:reactor-test:3.4.13")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
