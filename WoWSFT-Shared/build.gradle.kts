import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.springframework.boot.gradle.tasks.bundling.BootJar

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
val bootJar: BootJar by tasks
bootJar.enabled = false
jar.enabled = true