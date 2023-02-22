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
includeBuild("../spring-boot-libs")

// == Define the inner structure of this component ==
rootProject.name = "batista" // the component name
include("base")
include("mds-ssf")
