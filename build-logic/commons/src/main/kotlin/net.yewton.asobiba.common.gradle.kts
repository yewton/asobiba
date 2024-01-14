plugins {
    java
    id("net.yewton.asobiba.spotless")
    id("net.yewton.asobiba.container-test")
    id("net.yewton.asobiba.devonly")
    id("jacoco")
}

group = "net.yewton.asobiba"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("net.yewton.asobiba.platform:product-platform"))
    testImplementation(platform("net.yewton.asobiba.platform:test-platform"))

    annotationProcessor(platform("net.yewton.asobiba.platform:annotation-processor-platform"))

    implementation("org.jetbrains:annotations")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

// https://github.com/gradle/gradle/issues/1534#issuecomment-605868060
val localPropertiesFile = projectDir.resolve("local.properties")
if (localPropertiesFile.exists()) {
    `java.util`.Properties().run {
        load(localPropertiesFile.inputStream())
        forEach { (k, v) -> if (k is String) extra[k] = v }
    }
}

// 個々のプロジェクトではテストレポートを出力しない ( aggregation プロジェクトで行う )
tasks.jacocoTestReport.configure {
    enabled = false
}

tasks.named("devOnly") {
    dependsOn(tasks.named("spotlessApply"))
}
