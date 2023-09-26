plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(platform("net.yewton.asobiba.platform:plugins-platform"))

    implementation(project(":kotlin"))
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin")
    implementation("org.springframework.boot:spring-boot-gradle-plugin")
    implementation("io.spring.dependency-management:io.spring.dependency-management.gradle.plugin")
    implementation("org.jetbrains.kotlin:kotlin-allopen")
}
