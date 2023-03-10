import com.diffplug.gradle.spotless.SpotlessTask
import com.github.gradle.node.npm.task.NpmSetupTask
import com.github.gradle.node.task.NodeSetupTask

plugins {
    id("net.yewton.asobiba.node")
    id("com.github.node-gradle.node")
    id("com.diffplug.spotless")
}

val nodeDir = project.tasks.named<NodeSetupTask>(NodeSetupTask.NAME).get().nodeDir.get()
val npmDir = project.tasks.named<NpmSetupTask>(NpmSetupTask.NAME).get().npmDir.get()

val isWin = System.getProperty("os.name").lowercase().contains("windows")

val nodeExec = if (isWin) "node.exe" else "bin/node"
val npmExec = if (isWin) "npm.cmd" else "bin/npm"

val nodeExecPath = nodeDir.file(nodeExec)
val npmExecPath = npmDir.file(npmExec)

spotless {
    typescript {
        target("src/*/ts/**/*.ts", "src/*/front/**/*.ts")
        eslint()
            .nodeExecutable(nodeExecPath)
            .npmExecutable(npmExecPath)
            .configFile(".eslintrc.cjs")
            .tsconfigFile("tsconfig.json")
    }

    format("scss") {
        target("src/*/styles/**/*.scss")
        prettier()
            .nodeExecutable(nodeExecPath)
            .npmExecutable(npmExecPath)
    }
}

listOf("Typescript", "Scss").forEach {
    project.tasks.named<SpotlessTask>("spotless${it}").configure {
        dependsOn(tasks.nodeSetup, tasks.npmSetup)
    }
}
