import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.72" apply false
    id("org.springframework.boot") version "2.3.1.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.9.RELEASE" apply false
}

allprojects {
    group = "WoWSFT"
    version = "0.10.8.0-SNAPSHOT"

    repositories {
        jcenter()
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    dependencies {
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        implementation(kotlin("reflect"))
        implementation(kotlin("stdlib-jdk8"))
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }
}