import com.github.gradle.node.npm.task.NpxTask

plugins {
    id("net.yewton.asobiba.kotlin-common")
    id("net.yewton.asobiba.node")
}

val excludeProjects = listOf("build-logic", "platforms")
val subProjects = gradle.includedBuilds.map { it.name }.filterNot { excludeProjects.contains(it) }

listOf(("build" to "build"),
        ("verification" to "check"),
        ("build" to "clean"),
        ("other" to "detektAndCorrect"),
        ("other" to "spotlessApply")).forEach { (groupName, task) ->
    tasks.register("${task}All") {
        group = groupName
        description = "Run all $task"
        subProjects.forEach {
            dependsOn(gradle.includedBuild(it).task(":${task}All"))
        }
    }
}

// https://zenn.dev/cybozu_ept/articles/compare-renovate-dry-run
val renovateDebug by tasks.registering(NpxTask::class) {
    command.set("renovate@36.93.5")
    args.add("--platform=local")
    environment.set(mapOf(
            "RENOVATE_TOKEN" to (findProperty("renovate.token") as? String ?: ""),
            "LOG_LEVEL" to (findProperty("renovate.loglevel") as? String ?: "DEBUG"),
            "RENOVATE_CONFIG_FILE" to layout.projectDirectory.file("renovate.json").toString()
    ))
}
