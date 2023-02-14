import net.yewton.asobiba.gradle.ViteTask
import net.yewton.asobiba.gradle.VitePluginExtension

plugins {
    id("net.yewton.asobiba.node")
    id("com.github.node-gradle.node")
    id("java")
}

val viteExtension = project.extensions.create<VitePluginExtension>("vite")
viteExtension.sourceDir.convention(file("src/main/front"))
viteExtension.outputsDir.convention(file("src/main/resources/static/front"))

val viteBuild by tasks.registering(ViteTask::class) {
    inputs.dir(viteExtension.sourceDir.get())
    outputs.dir(viteExtension.outputsDir.get())
    args.set(listOf("build"))
}

@Suppress("UnstableApiUsage")
tasks.processResources {
    dependsOn(viteBuild)
}
