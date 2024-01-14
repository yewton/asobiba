plugins {
    `kotlin-dsl`
    id("net.yewton.asobiba.detekt")
}

dependencies {
    implementation(platform("net.yewton.asobiba.platform:plugins-platform"))

    implementation("com.github.node-gradle:gradle-node-plugin")
}
