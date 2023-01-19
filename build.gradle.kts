val excludeProjects = listOf("build-logic", "platforms")
val subProjects = gradle.includedBuilds.map { it.name }.filterNot { excludeProjects.contains(it) }

tasks.register("buildAll") {
    group = "build"
    description = "Run all builds"
    subProjects.forEach {
        dependsOn(gradle.includedBuild(it).task(":buildAll"))
    }
}

tasks.register("checkAll") {
    group = "verification"
    description = "Run all checks"
    subProjects.forEach {
        dependsOn(gradle.includedBuild(it).task(":checkAll"))
    }
}

tasks.register("cleanAll") {
    group = "build"
    description = "Run clean for all"
    subProjects.forEach {
        dependsOn(gradle.includedBuild(it).task(":cleanAll"))
    }
}
