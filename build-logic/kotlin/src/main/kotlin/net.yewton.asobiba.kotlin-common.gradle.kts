plugins {
    id("net.yewton.asobiba.common")
    id("net.yewton.asobiba.detekt")
    id("java")
    kotlin("jvm")
}

dependencies {
    implementation(platform("net.yewton.asobiba.platform:product-platform"))
    testImplementation(platform("net.yewton.asobiba.platform:test-platform"))

    testImplementation("io.kotest:kotest-assertions-core")
    testImplementation("io.kotest:kotest-runner-junit5")
}
