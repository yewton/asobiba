import com.diffplug.gradle.spotless.SpotlessTask
import com.github.gradle.node.npm.task.NpmSetupTask

plugins {
    id("net.yewton.asobiba.node")
    id("com.github.node-gradle.node")
    id("com.diffplug.spotless")
}

val npmExec = if (System.getProperty("os.name").lowercase().contains("windows")) {
    "/npm.cmd"
} else { "/bin/npm" }
val npmExecPath = "${project.tasks.named<NpmSetupTask>("npmSetup").get().npmDir.get()}${npmExec}"

spotless {
    typescript {
        target("src/*/ts/**/*.ts", "src/*/front/**/*.ts")
        eslint()
                .npmExecutable(npmExecPath)
                .configFile(".eslintrc.cjs")
                .tsconfigFile("tsconfig.json")
    }

    format("scss") {
        target("src/*/styles/**/*.scss")
        prettier()
                .npmExecutable(npmExecPath)

    }
}

listOf("Typescript", "Scss").forEach {
    project.tasks.named<SpotlessTask>("spotless${it}").configure {
        dependsOn(tasks.nodeSetup, tasks.npmInstall)
    }
}
