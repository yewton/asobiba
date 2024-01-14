dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
    }
}

includeBuild("../platforms")

rootDir.listFiles { it -> it.isDirectory && !it.name.startsWith(".") }
    ?.forEach { include(it.name) }

rootProject.name = "build-logic-lint"
