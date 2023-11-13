plugins {
    id("com.diffplug.spotless")
    id("net.yewton.asobiba.devonly")
}

spotless {
    encoding(`java.nio.charset`.StandardCharsets.UTF_8.name())

    format("text") {
        target(
                ".gitignore",
                ".gitattributes",
                "**/*.toml",
                "**/*.properties",
                "**/*.yml"
        )
        targetExclude("**/node_modules/**/*", "**/.gradle/**/*")
        trimTrailingWhitespace()
        endWithNewline()
    }

    java {
        importOrder()
        removeUnusedImports()
        googleJavaFormat()
        targetExclude("build/generated/**/*.java")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

tasks.named("devOnly") {
    dependsOn(tasks.named("spotlessApply"))
}
