/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    kotlin("jvm")
    id("com.diffplug.spotless")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(requiredVersionFromLibs("java")))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = requiredVersionFromLibs("java")
    }
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    constraints {
        // Define dependency versions as constraints
        implementation(dependencyFromLibs("commons-text"))

        implementation(dependencyFromLibs("kotlin-stdlib-jdk8"))
    }

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation(dependencyFromLibs("kotlin-stdlib-jdk8"))

    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    implementation(dependencyFromLibs("jetbrains-annotations"))

    testImplementation(platform(dependencyFromLibs("junit-bom")))

    testImplementation(bundleFromLibs("kotest"))
}

tasks.withType<Test>().configureEach {
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
    encoding("UTF-8")
    pluginManager.withPlugin("java") {
        java {
            importOrder()
            removeUnusedImports()
            googleJavaFormat()
            targetExclude("build/generated/**/*.java")
        }
    }

    pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
        kotlin {
            diktat().configFile("${rootDir}/diktat-analysis.yml")
        }
        kotlinGradle {
            diktat().configFile("${rootDir}/diktat-analysis.yml")
        }
    }
}

