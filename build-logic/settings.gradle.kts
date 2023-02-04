dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
    }
}

includeBuild("../platforms")

rootProject.name = "build-logic"
include("commons")
include("kotlin")
include("lifecycle")
include("spring-boot-application")