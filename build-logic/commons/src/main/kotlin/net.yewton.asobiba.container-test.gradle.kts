plugins {
    java
}

// https://docs.gradle.org/current/userguide/java_testing.html#sec:configuring_java_integration_tests

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

val containerTestTask by tasks.registering(Test::class) {
    description = "Runs container tests."
    group = LifecycleBasePlugin.VERIFICATION_GROUP

    containerTest.let {
        testClassesDirs = it.output.classesDirs
        classpath = it.runtimeClasspath
    }

    useJUnitPlatform()
    val enablePropertyName = "enableContainerTest"
    onlyIf("$enablePropertyName が指定されていない場合はスキップします") {
        providers.gradleProperty(enablePropertyName).isPresent
    }
}

tasks.check {
    dependsOn(containerTestTask)
}
