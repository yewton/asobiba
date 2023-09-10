pluginManagement {
    includeBuild("../build-logic")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

includeBuild("../platforms")
includeBuild("../example-app")
includeBuild("../osunaba")

rootProject.name = "aggregation"
include("test-coverage")

