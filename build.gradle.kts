plugins {
	java
	id("org.springframework.boot") version "3.5.6"
	id("io.spring.dependency-management") version "1.1.7"
	id("checkstyle")
	id("jacoco")
}

checkstyle {
	toolVersion = "11.1.0"
	configFile = file("config/checkstyle/checkstyle.xml")
}

jacoco {
	toolVersion = "0.8.13"
	reportsDirectory = layout.buildDirectory.dir("reports/jacoco")
}

tasks.withType<Checkstyle> {
	reports {
		xml.required.set(false)
		html.required.set(true)
	}
}

group = "com.markandz"
version = "0.0.1-SNAPSHOT"
description = "Demo project for Spring Boot"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.liquibase:liquibase-core")
	implementation("org.springframework.boot:spring-boot-starter-actuator:3.5.6")
	implementation("org.springframework.boot:spring-boot-docker-compose:3.5.6")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
		html.required.set(true)
	}
}

tasks.register<JacocoCoverageVerification>("jacocoCoverageVerification"){
	dependsOn(tasks.test)
	violationRules{
		rule{
			limit{
				minimum = 0.8.toBigDecimal()
			}
		}
	}
}
