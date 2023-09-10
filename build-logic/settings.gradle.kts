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
include("spring-boot")
include("spotless-node")
include("node")
include("report-aggregation")
