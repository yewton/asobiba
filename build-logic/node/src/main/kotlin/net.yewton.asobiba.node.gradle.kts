import com.github.gradle.node.NodeExtension

plugins {
    id("com.github.node-gradle.node")
}

configure<NodeExtension> {
    download.set(true)
    version.set("18.16.0")
    npmVersion.set("9.6.7")
}

tasks.npmInstall {
    nodeModulesOutputFilter {
        // 何故か npm install するたびに変更されてしまうので
        // https://github.com/node-gradle/gradle-node-plugin/blob/master/docs/faq.md#how-do-i-ignore-some-files-of-the-node_modules-directory-that-are-modified-by-the-build-and-prevent-tasks-from-being-up-to-date-
        exclude("@esbuild")
    }
}
