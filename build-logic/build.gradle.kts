allprojects {
    group = "net.yewton.asobiba.build-logic"
}

// https://discuss.gradle.org/t/gradle-clean-all-projects/10618/3
val cleanAll by tasks.registering {
    group = "build"
}

subprojects {
    cleanAll {
        dependsOn(tasks.matching { it.name == "clean" })
    }
}
