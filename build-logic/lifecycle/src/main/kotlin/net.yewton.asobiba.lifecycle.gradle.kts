import org.gradle.configurationcache.extensions.capitalized

listOf(("build" to listOf("build", "clean")),
    ("verification" to listOf("check", "containerTest"))).forEach { (groupName, taskNames) ->
    taskNames.forEach { taskName ->
        tasks.register("${taskName}All") {
            group = groupName
            description = "${taskName.capitalized()} all of the '${project.name}' component"
            subprojects {
                this@register.dependsOn(provider { tasks.matching { it.name == taskName } })
            }
        }
    }
}

tasks.register("autoCorrectAll") {
    group = "verification"
    subprojects {
        // タスクが未定義だった場合に findByName だと null になってエラーになる
        val autoCorrectTasks = provider {
            tasks.matching { listOf("spotlessApply", "detektAndCorrect").contains(it.name) }
        }
        this@register.dependsOn(autoCorrectTasks)
    }
}
