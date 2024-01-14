allprojects {
    group = "net.yewton.asobiba.build-logic"
}

// https://discuss.gradle.org/t/gradle-clean-all-projects/10618/3
tasks.register("cleanAll") {
    group = BasePlugin.BUILD_GROUP
    subprojects.forEach { project ->
        dependsOn(project.tasks.matching { it.name == "clean" })
    }
}

tasks.register("autoCorrectAll") {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    subprojects.forEach { project ->
        dependsOn(project.tasks.matching { it.name == "detektAndCorrect" })
    }
}
