// == Define locations for build logic ==
pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    includeBuild("../build-logic")
}

// == Define locations for components ==
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
includeBuild("../platforms")

// == Define the inner structure of this component ==
rootProject.name = "osunaba" // the component name
include("mondai")
include("reactive-webapp")
