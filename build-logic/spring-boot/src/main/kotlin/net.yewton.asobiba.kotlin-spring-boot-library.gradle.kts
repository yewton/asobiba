import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("net.yewton.asobiba.kotlin-common")
    id("java")
    id("net.yewton.asobiba.kotlin-spring-boot-dependency")
    kotlin
    kotlin("plugin.spring")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}
