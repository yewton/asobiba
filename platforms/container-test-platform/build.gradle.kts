plugins {
    id("java-platform")
}

group = "net.yewton.asobiba.platform"

javaPlatform.allowDependencies()

dependencies {
    api(platform(libs.testcontainers.bom))
}
