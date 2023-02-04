import com.github.gradle.node.NodeExtension
import com.github.gradle.node.npm.task.NpmInstallTask
import org.springframework.boot.gradle.plugin.ResolveMainClassName
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import org.springframework.boot.gradle.tasks.bundling.BootJar
import org.springframework.boot.gradle.tasks.run.BootRun

buildscript {
    dependencies {
        classpath("com.github.node-gradle:gradle-node-plugin")
    }
}

plugins {
    id("net.yewton.asobiba.kotlin-spring-boot")
}

apply(plugin = "com.github.node-gradle.node")

extensions.configure<NodeExtension> {
    download.set(true)
    version.set("18.12.0")
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

@Suppress("UnstableApiUsage")
tasks.named<ProcessResources>(preview.processResourcesTaskName) {
    filesMatching("**/application.yml") {
        expand(
            // 相対パス指定は実行ディレクトリによって意味が変わってしまう為、
            // プロジェクトのディレクトリを使って絶対パス指定できるようにする
            "projectDir" to projectDir
        )
    }
}

val npmInstallTask = tasks.named<NpmInstallTask>(NpmInstallTask.Companion.NAME)

val viteBuild by tasks.registering(com.github.gradle.node.npm.task.NpxTask::class) {
    dependsOn(npmInstallTask)
    command.set("vite")
    args.set(listOf("build"))
    inputs.files(
        "package.json",
        "package-lock.json",
        "tsconfig.json",
        "vite.config.ts"
    )
    inputs.dir("src/frontend")
    inputs.dir(fileTree("node_modules").exclude(".cache"))
    outputs.dir("src/main/resources/static")
}

@Suppress("UnstableApiUsage")
tasks.withType(ProcessResources::class).configureEach {
    dependsOn(viteBuild)
}
