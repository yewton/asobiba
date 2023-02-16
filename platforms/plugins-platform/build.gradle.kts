plugins {
    id("java-platform")
}

group = "net.yewton.asobiba.platform"

dependencies {
    constraints {
        // TODO https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto.build.generate-git-info
        api(libs.kotlin.jvm.gradle.plugin)
        api(libs.spotless.plugin)
        api(libs.detekt.plugin)
        api(libs.kotlin.spring.plugin)
        api(libs.spring.boot.plugin)
        api(libs.spring.dependency.management.plugin)
        api(libs.node.plugin)
    }
}
