plugins {
    java
    `jvm-test-suite`
}

val containerTest: SourceSet by sourceSets.creating {
    compileClasspath += sourceSets.main.get().output
    runtimeClasspath += sourceSets.main.get().output
}

val containerTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}
configurations[containerTest.runtimeOnlyConfigurationName].extendsFrom(configurations.testRuntimeOnly.get())

dependencies {
    containerTestImplementation(platform("net.yewton.asobiba.platform:container-test-platform"))
}

tasks.register<Test>("containerTest") {
    description = "Runs container tests."
    group = "verification"

    containerTest.let {
        testClassesDirs = it.output.classesDirs
        classpath = it.runtimeClasspath
    }

    useJUnitPlatform()
}
