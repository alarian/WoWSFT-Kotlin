import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {

}

dependencies {
    implementation(project(":WoWSFT-Shared"))
}

sourceSets {
    main {
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDir("src/main/kotlin")
        }
        resources.srcDirs(listOf("src/main/resources"))
    }
}