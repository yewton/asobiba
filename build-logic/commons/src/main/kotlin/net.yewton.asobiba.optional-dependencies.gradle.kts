// https://github.com/spring-projects/spring-boot/blob/v3.0.2/buildSrc/src/main/java/org/springframework/boot/build/optional/OptionalDependenciesPlugin.java

val optional: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = false
}

plugins.withType<JavaPlugin>().configureEach {
    val sourceSets = project.extensions.getByType<JavaPluginExtension>().sourceSets
    sourceSets.all {
        project.configurations.getByName(compileClasspathConfigurationName)
                .extendsFrom(optional)
        project.configurations.getByName(runtimeClasspathConfigurationName)
                .extendsFrom(optional)
    }
}
