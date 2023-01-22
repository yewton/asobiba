import io.gitlab.arturbosch.detekt.Detekt

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    kotlin("jvm")
    id("java")
    id("com.diffplug.spotless")
    id("io.gitlab.arturbosch.detekt")
}

group = "net.yewton.asobiba"

kotlin {
    jvmToolchain(17)
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation(platform("net.yewton.asobiba.platform:product-platform"))
    testImplementation(platform("net.yewton.asobiba.platform:test-platform"))
    detektPlugins(platform("net.yewton.asobiba.platform:detekt-plugins-platform"))

    // Use the Kotlin JDK 8 standard library.
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains:annotations")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting")

    testImplementation("io.kotest:kotest-assertions-core")
    testImplementation("io.kotest:kotest-runner-junit5")
}

tasks.test {
    useJUnitPlatform()
}

testing {
    suites {
        // Configure the built-in test suite
        getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter()
        }
    }
}

spotless {
    encoding(`java.nio.charset`.StandardCharsets.UTF_8.name())

    format("text") {
        target(
                ".gitignore",
                ".gitattributes",
                "**/*.toml",
                "**/*.properties",
                "**/*.yml"
        )
        trimTrailingWhitespace()
        endWithNewline()
    }

    pluginManager.withPlugin("java") {
        java {
            importOrder()
            removeUnusedImports()
            googleJavaFormat()
            targetExclude("build/generated/**/*.java")
            trimTrailingWhitespace()
            endWithNewline()
        }
    }
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
