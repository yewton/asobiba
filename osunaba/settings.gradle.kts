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
include("mondai") // https://github.com/PacktPublishing/Java-Coding-Problems
include("challenge") // https://github.com/Apress/java-challenges
include("reactive-webapp")
include("jcp2") // https://github.com/PacktPublishing/Java-Coding-Problems-Second-Edition
include("mcp-server")
include("mcp-client")
include("leetcode")
