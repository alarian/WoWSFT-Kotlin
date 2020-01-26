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
    }
}

val jar: Jar by tasks
jar.enabled = true