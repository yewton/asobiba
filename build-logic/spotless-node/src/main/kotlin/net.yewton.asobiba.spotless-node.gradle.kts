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
    // https://github.com/node-gradle/gradle-node-plugin/blob/7.0.0/src/main/kotlin/com/github/gradle/node/npm/exec/NpmExecRunner.kt#L70
    val variantComputer = VariantComputer()
    val nodeExtension = project.extensions.getByType<NodeExtension>()
    val nodeDirProvider = nodeExtension.resolvedNodeDir
    val npmDirProvider = variantComputer.computeNpmDir(nodeExtension, nodeDirProvider)
    val nodeBinDirProvider = variantComputer.computeNodeBinDir(nodeDirProvider, nodeExtension.resolvedPlatform)
    val npmBinDirProvider = variantComputer.computeNpmBinDir(npmDirProvider, nodeExtension.resolvedPlatform)
    Pair(
        computeNodeExec(nodeExtension, nodeBinDirProvider),
        variantComputer.computeNpmExec(nodeExtension, npmBinDirProvider)
    )
}

spotless {
    format("scss") {
        target("src/*/styles/**/*.scss")
        prettier()
            .nodeExecutable(nodeExecPath.get())
            .npmExecutable(npmExecPath.get())
    }
}

listOf("Scss").forEach {
    project.tasks.named<SpotlessTask>("spotless$it").configure {
        dependsOn(tasks.nodeSetup, tasks.npmSetup, tasks.npmInstall)
    }
}
