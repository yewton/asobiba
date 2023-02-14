import net.yewton.asobiba.gradle.ViteTask

plugins {
    id("net.yewton.asobiba.kotlin-spring-boot-application")
    id("net.yewton.asobiba.vite")
}

group = "$group.nanka"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val preview: SourceSet by sourceSets.creating {
    compileClasspath += sourceSets.main.get().output
    runtimeClasspath += sourceSets.main.get().output
    resources.setSrcDirs(resources.srcDirs + sourceSets.main.get().resources.srcDirs)
}

val previewImplementation: Configuration =
    configurations[preview.implementationConfigurationName].extendsFrom(configurations.implementation.get())
configurations[preview.runtimeOnlyConfigurationName].extendsFrom(configurations.runtimeOnly.get())

dependencies {
    previewImplementation("net.yewton.asobiba.spring-boot-libs:commons")
    previewImplementation("net.yewton.asobiba.spring-boot-libs:devtools")
    previewImplementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    previewImplementation("org.springframework.boot:spring-boot-starter-web")
    previewImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    previewImplementation("org.jetbrains.kotlin:kotlin-reflect")
    previewImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    previewImplementation("org.springframework.boot:spring-boot-starter-actuator")
    previewImplementation("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.bootJar {
    classpath = preview.compileClasspath
}

tasks.bootRun {
    classpath = preview.runtimeClasspath
}

tasks.resolveMainClassName {
    classpath = preview.runtimeClasspath
}

val viteBuildPreview by tasks.registering(ViteTask::class) {
    inputs.dir(file("src/preview/front"))
    outputs.dir(file("src/preview/resources/static/preview/front"))
    environment.set(mapOf("PREVIEW" to "1"))
    args.set(listOf("build"))
}

@Suppress("UnstableApiUsage")
tasks.named<ProcessResources>(preview.processResourcesTaskName).configure {
    dependsOn(tasks.viteBuild, viteBuildPreview)
}
