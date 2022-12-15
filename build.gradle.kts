@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once https://github.com/gradle/gradle/issues/22797 is fixed
plugins {
    alias(libs.plugins.versions)
    alias(libs.plugins.version.catalog.update)
}

tasks.dependencyUpdates {
    checkConstraints = true
    checkForGradleUpdate = true
    resolutionStrategy {
        componentSelection {
            all {
                val rejected = listOf("alpha", "beta", "rc", "cr", "m", "preview", "b", "ea")
                        .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-+]*") }
                        .any { it.matches(candidate.version) }
                if (rejected) {
                    reject("Release candidate")
                }
            }
        }
    }
}
