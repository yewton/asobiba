plugins {
    id("java-platform")
}

group = "net.yewton.asobiba.platform"

// allow the definition of dependencies to other platforms like the Spring Boot BOM
javaPlatform.allowDependencies()

dependencies {
    // Align versions of all Kotlin components
    api(platform(libs.kotlin.bom))
    constraints {
        api(libs.commons.text)
        api(libs.jetbrains.annotations)
        api(libs.springdoc.openapi.starter.common)
        api(libs.springdoc.openapi.starter.webflux.ui)
        api(libs.therapi.runtime.javadoc.core)
        api(libs.resilience4j.spring.boot3)
        api(libs.resilience4j.reactor)
    }
}
