plugins {
    // Apply the common convention plugin for shared build configuration between library and application projects.
    id("net.yewton.asobiba.kotlin-common")

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}
