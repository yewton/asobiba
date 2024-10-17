import com.github.gradle.node.npm.task.NpxTask

plugins {
    id("net.yewton.asobiba.node")
    java
}

val excludeProjects = listOf("build-logic", "build-logic-lint", "platforms", "btrace")
val subProjects = gradle.includedBuilds.map { it.name }.filterNot { excludeProjects.contains(it) }

listOf(
    LifecycleBasePlugin.BUILD_TASK_NAME,
    LifecycleBasePlugin.ASSEMBLE_TASK_NAME,
    LifecycleBasePlugin.CLEAN_TASK_NAME,
    LifecycleBasePlugin.CHECK_TASK_NAME
).forEach { taskName ->
    tasks.named(taskName) {
        dependsOn(subProjects.map { gradle.includedBuild(it).task(":${taskName}") })
    }
}

tasks.register("autoCorrect") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    dependsOn(subProjects.map {  gradle.includedBuild(it).task(":$name") })
    // build-logic は autoCorrect のみ対応
    dependsOn(gradle.includedBuild("build-logic").task(":autoCorrect"))
}

// https://zenn.dev/cybozu_ept/articles/compare-renovate-dry-run
val renovateDebug by tasks.registering(NpxTask::class) {
    command.set("renovate@38.126.1")
    args.add("--platform=local")
    environment.set(
        mapOf(
            "RENOVATE_TOKEN" to (findProperty("renovate.token") as? String ?: ""),
            "LOG_LEVEL" to (findProperty("renovate.loglevel") as? String ?: "DEBUG"),
            "RENOVATE_CONFIG_FILE" to layout.projectDirectory.file("renovate.json").toString()
        )
    )
}

val npmUpgradeAll by tasks.registering {
    listOf("view", "webapp").forEach {
        dependsOn(gradle.includedBuild("nanka").task(":$it:npmUpgrade"))
    }
}

val npmInstallAll by tasks.registering {
    listOf("view", "webapp").forEach {
        dependsOn(gradle.includedBuild("nanka").task(":$it:npmInstall"))
    }
}

// IntelliJ IDEA プロジェクトのデフォルトがこれで決まる
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}
