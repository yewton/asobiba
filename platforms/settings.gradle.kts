dependencyResolutionManagement { // for gradle-versions-plugin
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "platforms"

include("product-platform")
include("test-platform")
include("plugins-platform")
include("detekt-plugins-platform")
include("annotation-processor-platform")
include("container-test-platform")
