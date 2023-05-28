import com.diffplug.gradle.spotless.SpotlessTask
import com.github.gradle.node.NodeExtension
import com.github.gradle.node.variant.VariantComputer
import com.github.gradle.node.variant.computeNodeExec

plugins {
    id("net.yewton.asobiba.node")
    id("com.github.node-gradle.node")
    id("com.diffplug.spotless")
}

val (nodeExecPath, npmExecPath) = run {
    // https://github.com/node-gradle/gradle-node-plugin/blob/5.0.0/src/main/kotlin/com/github/gradle/node/npm/exec/NpmExecRunner.kt#L78
    val variantComputer = VariantComputer()
    val nodeExtension = project.extensions.getByType<NodeExtension>()
    val nodeDirProvider = nodeExtension.computedNodeDir
    val npmDirProvider = variantComputer.computeNpmDir(nodeExtension, nodeDirProvider)
    val nodeBinDirProvider = variantComputer.computeNodeBinDir(nodeDirProvider)
    val npmBinDirProvider = variantComputer.computeNpmBinDir(npmDirProvider)
    Pair(
        computeNodeExec(nodeExtension, nodeBinDirProvider),
        variantComputer.computeNpmExec(nodeExtension, npmBinDirProvider))
}

spotless {
    typescript {
        target("src/*/ts/**/*.ts", "src/*/front/**/*.ts")
        eslint()
            .nodeExecutable(nodeExecPath.get())
            .npmExecutable(npmExecPath.get())
            .configFile(".eslintrc.cjs")
            .tsconfigFile("tsconfig.json")
    }

    format("scss") {
        target("src/*/styles/**/*.scss")
        prettier()
            .nodeExecutable(nodeExecPath.get())
            .npmExecutable(npmExecPath.get())
    }
}

listOf("Typescript", "Scss").forEach {
    project.tasks.named<SpotlessTask>("spotless${it}").configure {
        dependsOn(tasks.nodeSetup, tasks.npmSetup)
    }
}
