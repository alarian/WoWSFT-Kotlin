import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.springframework.boot.gradle.tasks.bundling.BootWar

plugins {
    id("war")
}

dependencies {
    implementation(project(":WoWSFT-Shared"))

    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

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
}