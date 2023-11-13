plugins {
    java
}

val devOnly by tasks.registering
// https://docs.github.com/en/actions/learn-github-actions/variables#default-environment-variables
if (System.getenv("CI")?.equals("true") != true) {
    tasks.named(JavaPlugin.CLASSES_TASK_NAME) {
        dependsOn(devOnly)
    }
}
