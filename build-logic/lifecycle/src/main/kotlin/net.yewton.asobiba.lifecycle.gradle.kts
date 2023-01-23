listOf(("build" to "build"),
        ("build" to "clean"),
        ("verification" to "check"),
        ("other" to "detektAndCorrect"),
        ("other" to "spotlessApply")).forEach { (groupName, task) ->
    tasks.register("${task}All") {
        group = groupName
        description = "${task.capitalize()} all of the '${project.name}' component"
        dependsOn(subprojects.map { ":${it.name}:${task}"} )
    }
}
