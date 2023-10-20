import com.github.gradle.node.npm.task.NpxTask

plugins {
    id("net.yewton.asobiba.kotlin-common")
    id("net.yewton.asobiba.node")
}

val excludeProjects = listOf("build-logic", "platforms", "btrace")
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

// https://zenn.dev/cybozu_ept/articles/compare-renovate-dry-run
val renovateDebug by tasks.registering(NpxTask::class) {
    command.set("renovate@37.31.1")
    args.add("--platform=local")
    environment.set(
        mapOf(
            "RENOVATE_TOKEN" to (findProperty("renovate.token") as? String ?: ""),
            "LOG_LEVEL" to (findProperty("renovate.loglevel") as? String ?: "DEBUG"),
            "RENOVATE_CONFIG_FILE" to layout.projectDirectory.file("renovate.json").toString()
        )
    )
}
