import com.github.gradle.node.npm.task.NpxTask

plugins {
    id("net.yewton.asobiba.node")
    java
}

val excludeProjects = listOf("build-logic", "build-logic-lint", "platforms", "btrace")
val subProjects = gradle.includedBuilds.map { it.name }.filterNot { excludeProjects.contains(it) }

listOf(
    ("build" to listOf("build", "clean")),
    ("verification" to listOf("check", "containerTest", "autoCorrect"))
).forEach { (groupName, taskNames) ->
    taskNames.forEach {
        tasks.register("${it}All") {
            group = groupName
            description = "Run all $it"
            dependsOn(tasks.matching { task -> it == task.name })
            subProjects.forEach { project ->
                dependsOn(gradle.includedBuild(project).task(":${it}All"))
            }
        }
    }
}

tasks.named("autoCorrectAll") {
    // build-logic は autoCorrectAll のみ対応
    dependsOn(gradle.includedBuild("build-logic").task(":autoCorrectAll"))
}

// https://zenn.dev/cybozu_ept/articles/compare-renovate-dry-run
val renovateDebug by tasks.registering(NpxTask::class) {
    command.set("renovate@37.371.0")
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
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
