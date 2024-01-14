plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(platform("net.yewton.asobiba.platform:plugins-platform"))
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin")
}
