/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

group = "$group.example-app"

plugins {
    id("net.yewton.asobiba.kotlin-application")
}

dependencies {
    implementation("org.apache.commons:commons-text")
    implementation(project(":utilities"))
}

application {
    // Define the main class for the application.
    mainClass.set("net.yewton.asobiba.app.AppKt")
}
