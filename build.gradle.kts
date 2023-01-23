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
