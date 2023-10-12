import org.gradle.configurationcache.extensions.capitalized

listOf(("build" to "build"),
        ("build" to "clean"),
        ("verification" to "check"),
        ("verification" to "containerTest")).forEach { (groupName, task) ->
    tasks.register("${task}All") {
        group = groupName
        description = "${task.capitalized()} all of the '${project.name}' component"
        dependsOn(provider {
            subprojects.mapNotNull {
                it.tasks.findByName(task)
            }
        })
    }
}

val autoCorrectAll by tasks.registering {
    group = "verification"
}

subprojects {
    // タスクが未定義だった場合に findByName だと null になってエラーになる
    val autoCorrectTasks = provider {
        tasks.matching { listOf("spotlessApply", "detektAndCorrect").contains(it.name) }
    }
    autoCorrectAll {
        dependsOn(autoCorrectTasks)
    }
}
