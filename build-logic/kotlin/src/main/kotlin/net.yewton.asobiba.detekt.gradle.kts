import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("io.gitlab.arturbosch.detekt")
    kotlin
}

dependencies {
    detektPlugins(platform("net.yewton.asobiba.platform:detekt-plugins-platform"))

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting")
}

tasks.register<Detekt>("detektAndCorrect") {
    autoCorrect = true
}

tasks.withType<Detekt>().configureEach {
    setSource(files(
            project.sourceSets.map { it.kotlin.srcDirs },
            projectDir.listFiles { file -> file.name.matches(Regex(""".*\.kts?$""")) }))
    config.setFrom(files("${rootDir.parent}/config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    basePath = projectDir.absolutePath
    include("**/*.kt", "**/*.kts")
}
