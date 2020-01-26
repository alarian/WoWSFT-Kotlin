import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {

}

dependencies {
    implementation(project(":WoWSFT-Shared"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework:spring-context-support")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

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