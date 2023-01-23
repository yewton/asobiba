import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    detektPlugins(platform("net.yewton.asobiba.platform:detekt-plugins-platform"))

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting")
}

tasks.register<Detekt>("detektAndCorrect") {
    autoCorrect = true
}

tasks.withType<Detekt>().configureEach {
    setSource(projectDir)
    config.setFrom(files("${rootDir.parent}/config/detekt/detekt.yml"))
    basePath = rootDir.absolutePath
    buildUponDefaultConfig = true
    exclude("build/")
    include("**/*.kt", "**/*.kts")
}
