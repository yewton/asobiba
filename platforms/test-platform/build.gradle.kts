plugins {
    id("java-platform")
}

group = "net.yewton.asobiba.platform"

// allow the definition of dependencies to other platforms like the JUnit 5 BOM
javaPlatform.allowDependencies()

dependencies {
    api(platform(libs.junit.bom))
    constraints {
        api(libs.kotest.assertions.core)
        api(libs.kotest.runner.junit5)
        api(libs.kotest.extensions.spring)
        api(libs.archunit.junit5)
    }
}
