package net.yewton.asobiba.gradle

import com.github.gradle.node.npm.task.NpmInstallTask
import com.github.gradle.node.npm.task.NpxTask

@Suppress("LeakingThis", "UnnecessaryAbstractClass")
abstract class ViteTask : NpxTask() {
    init {
        command.set("vite")
        inputs.files(
            "package.json",
            "package-lock.json",
            "tsconfig.json",
            "vite.config.ts"
        )
        inputs.dir(project.fileTree("node_modules").exclude(".cache"))
        dependsOn(NpmInstallTask.NAME)
    }
}
