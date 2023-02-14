package net.yewton.asobiba.gradle

import org.gradle.api.provider.Property
import java.io.File

interface VitePluginExtension {
    val sourceDir: Property<File>
    val outputsDir: Property<File>
}
