plugins {
    id("java-platform")
}

group = "net.yewton.asobiba.platform"

dependencies {
    constraints {
        api(libs.kotlin.jvm.gradle.plugin)
        api(libs.spotless.plugin)
        api(libs.kotlin.spring.plugin)
        api(libs.spring.boot.plugin)
        api(libs.spring.dependency.management.plugin)
    }
}
