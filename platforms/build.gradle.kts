import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    alias(libs.plugins.versions)
    alias(libs.plugins.version.catalog.update)
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    gradlePluginPortal()
}

tasks.withType<DependencyUpdatesTask> {
    checkConstraints = true
    checkBuildEnvironmentConstraints = true
    checkForGradleUpdate = true
    resolutionStrategy {
        componentSelection {
            all(Action {
                // Action を明示しないと、 Any として IDE 上解釈されてしまうっぽい
                // https://github.com/ben-manes/gradle-versions-plugin/blob/v0.46.0/gradle-versions-plugin/src/main/kotlin/com/github/benmanes/gradle/versions/updates/resolutionstrategy/ComponentSelectionRulesWithCurrent.kt#L37
                val rejected = listOf("alpha", "beta", "rc", "cr", "m", "preview", "b", "ea")
                        .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-+]*") }
                        .any { it.matches(candidate.version) }
                if (rejected) {
                    reject("Release candidate")
                }
            })
        }
    }
}

versionCatalogUpdate {
    keep {
        // plugin への依存が使ってない判定されちゃって消されちゃうので
        keepUnusedLibraries.set(true)
    }
}
