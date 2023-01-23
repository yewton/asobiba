plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(platform("net.yewton.asobiba.platform:plugins-platform"))

    implementation(project(":kotlin"))
    implementation("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin")
    implementation("org.springframework.boot:org.springframework.boot.gradle.plugin")
    implementation("io.spring.dependency-management:io.spring.dependency-management.gradle.plugin")
    implementation("org.jetbrains.kotlin.plugin.spring:org.jetbrains.kotlin.plugin.spring.gradle.plugin")
}
