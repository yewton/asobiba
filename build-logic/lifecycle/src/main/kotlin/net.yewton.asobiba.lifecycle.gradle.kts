import org.gradle.configurationcache.extensions.capitalized

listOf(("build" to "build"),
        ("build" to "clean"),
        ("verification" to "check"),
        ("verification" to "containerTest"),
        ("other" to "detektAndCorrect"),
        ("other" to "spotlessApply")).forEach { (groupName, task) ->
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
