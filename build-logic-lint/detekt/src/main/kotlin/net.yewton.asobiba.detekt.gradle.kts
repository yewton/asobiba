import io.gitlab.arturbosch.detekt.CONFIGURATION_DETEKT
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("io.gitlab.arturbosch.detekt")
    kotlin("jvm")
}

dependencies {
    detektPlugins(platform("net.yewton.asobiba.platform:detekt-plugins-platform"))
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting")
}

val detektAndCorrect by tasks.registering(Detekt::class) {
    autoCorrect = true
}

// プロジェクトルートでの実行時は rootDir に、配下プロジェクトでの実行時は rootDir.parent にある
val detektConfigCandidates = listOf(rootDir, rootDir.parent).map { "${it}/config/detekt/detekt.yml"}
tasks.withType<Detekt>().configureEach {
    setSource(files(
            project.sourceSets.flatMap { it.kotlin.srcDirs }.filterNot { it.path.contains("/build/") },
            projectDir.listFiles { file -> file.name.matches(Regex(""".*\.kts?$""")) }))
    config.setFrom(files(*detektConfigCandidates.toTypedArray()).filter { it.exists() })
    buildUponDefaultConfig = true
    basePath = projectDir.absolutePath
    include("**/*.kt", "**/*.kts")
}

// https://github.com/detekt/detekt/issues/6198#issuecomment-1611248496
project.afterEvaluate {
    configurations[CONFIGURATION_DETEKT].resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin") {
            @Suppress("UnstableApiUsage")
            useVersion(io.gitlab.arturbosch.detekt.getSupportedKotlinVersion())
        }
    }
}
