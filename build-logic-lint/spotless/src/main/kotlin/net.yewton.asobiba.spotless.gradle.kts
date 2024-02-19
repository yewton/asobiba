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
        palantirJavaFormat("2.39.0").style("GOOGLE").formatJavadoc(true)
        formatAnnotations()
        indentWithSpaces(2)
        targetExclude("build/generated/**/*.java")
        trimTrailingWhitespace()
        endWithNewline()
    }
}
