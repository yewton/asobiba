import com.github.gradle.node.NodeExtension
import com.github.gradle.node.npm.task.NpxTask

plugins {
    id("com.github.node-gradle.node")
}

configure<NodeExtension> {
    download.set(true)
    version.set("18.18.2")
    npmVersion.set("10.2.1")
}

tasks.npmInstall {
    nodeModulesOutputFilter {
        // 何故か npm install するたびに変更されてしまうので
        // https://github.com/node-gradle/gradle-node-plugin/blob/master/docs/faq.md#how-do-i-ignore-some-files-of-the-node_modules-directory-that-are-modified-by-the-build-and-prevent-tasks-from-being-up-to-date-
        exclude("@esbuild")
    }
}

fun registeringNpmCheckUpdateTask(customizer: NpxTask.() -> Unit) = tasks.registering(NpxTask::class) {
    command.set("npm-check-updates")
    args.set(listOf("--target", "minor"))
    customizer(this)
}

val npmCheckUpdate by registeringNpmCheckUpdateTask { }

val npmUpgrade by registeringNpmCheckUpdateTask {
    args.add("--upgrade")
}
