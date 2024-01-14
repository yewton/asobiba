plugins {
    // Support convention plugins written in Kotlin. Convention plugins are build scripts in 'src/main' that automatically become available as plugins in the main build.
    `kotlin-dsl`
}

dependencies {
    implementation(platform("net.yewton.asobiba.platform:plugins-platform"))
    implementation("com.diffplug.spotless:spotless-plugin-gradle")
}
