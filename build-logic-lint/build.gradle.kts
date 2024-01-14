allprojects {
    group = "net.yewton.asobiba.build-logic-lint"
}

// https://discuss.gradle.org/t/gradle-clean-all-projects/10618/3
tasks.register("cleanAll") {
    group = "build"
    subprojects.forEach { project ->
        dependsOn(project.tasks.matching { it.name == "clean" })
    }
}
