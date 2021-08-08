import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.springframework.boot.gradle.tasks.bundling.BootWar

plugins {
    id("war")
}

dependencies {
    implementation(project(":WoWSFT-Shared"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework:spring-context-support")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
//    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
}

val Project.profile get() = findProperty("profile") ?: "dev"

sourceSets {
    main {
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDir("src/main/kotlin")
        }
        resources.srcDirs(listOf("src/main/resources", "src/main/resources-$profile"))
    }
}

tasks.getByName<BootWar>("bootWar") {
    mainClassName = "WoWSFT.Application"

    from("src/main/ebextensions") {
        into(".ebextensions")
    }

    from("src/main/platform") {
        into(".platform")
    }
}