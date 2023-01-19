import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("net.yewton.asobiba.kotlin-common")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    kotlin("plugin.spring")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}
