import org.springframework.boot.gradle.plugin.ResolveMainClassName
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("net.yewton.asobiba.kotlin-spring-boot")
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
    previewImplementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    previewImplementation("org.springframework.boot:spring-boot-starter-web")
    previewImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    previewImplementation("org.jetbrains.kotlin:kotlin-reflect")
    previewImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    previewImplementation("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.named<BootJar>(SpringBootPlugin.BOOT_JAR_TASK_NAME) {
    classpath = preview.compileClasspath
}

tasks.named<BootRun>("bootRun") {
    classpath = preview.runtimeClasspath
}

tasks.named<ResolveMainClassName>(SpringBootPlugin.RESOLVE_MAIN_CLASS_NAME_TASK_NAME) {
    classpath = preview.runtimeClasspath
}
