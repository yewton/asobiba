plugins {
    alias(libs.plugins.versions)
    alias(libs.plugins.version.catalog.update)
}

tasks.dependencyUpdates {
    checkConstraints = true
}
