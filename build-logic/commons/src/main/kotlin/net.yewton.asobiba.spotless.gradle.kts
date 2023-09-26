plugins {
    id("com.diffplug.spotless")
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
