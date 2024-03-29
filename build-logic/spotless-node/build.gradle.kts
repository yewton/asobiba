plugins {
    `kotlin-dsl`
    id("net.yewton.asobiba.detekt")
}

dependencies {
    implementation(platform("net.yewton.asobiba.platform:plugins-platform"))

    implementation(project(":node"))
    implementation("com.github.node-gradle:gradle-node-plugin")
    implementation("com.diffplug.spotless:spotless-plugin-gradle")
}
