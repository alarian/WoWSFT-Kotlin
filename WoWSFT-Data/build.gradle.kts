import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

plugins {

}

dependencies {

}

sourceSets {
    main {
        withConvention(KotlinSourceSet::class) {
            kotlin.srcDir("src/main/kotlin")
        }
        resources.srcDirs(listOf("src/main/resources"))
    }
}