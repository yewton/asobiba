import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.the

// see https://github.com/junit-team/junit5/blob/r5.9.1/buildSrc/src/main/kotlin/ProjectExtensions.kt

fun Project.requiredVersionFromLibs(name: String) =
        libsVersionCatalog.findVersion(name).get().requiredVersion

fun Project.dependencyFromLibs(name: String) =
        libsVersionCatalog.findLibrary(name).get()

fun Project.bundleFromLibs(name: String) =
        libsVersionCatalog.findBundle(name).get()

private val Project.libsVersionCatalog: VersionCatalog
    get() = the<VersionCatalogsExtension>().named("libs")
