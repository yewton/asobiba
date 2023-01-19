dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
    }
}

includeBuild("../platforms")

rootProject.name = "build-logic"
include("commons")
include("lifecycle")
include("spring-boot-application")
