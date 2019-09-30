import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootWar

group = "WoWSFT"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_1_8

plugins {
    id("war")
    id("org.springframework.boot") version "2.1.7.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
    kotlin("plugin.allopen") version "1.2.71"
//    kotlin("kapt") version "1.3.50"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))

//    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

sourceSets {
    main {
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDir("src/main/kotlin")
        }
        resources.srcDirs(listOf("src/main/resources"))
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.getByName<BootWar>("bootWar") {
    mainClassName = "wowsft.Application"

    from("src/main/ebextensions") {
        into(".ebextensions")
    }
}
