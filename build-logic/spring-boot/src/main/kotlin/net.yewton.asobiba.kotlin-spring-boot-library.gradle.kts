import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("net.yewton.asobiba.kotlin-common")
    id("java")
    id("net.yewton.asobiba.kotlin-spring-boot-dependency")
    kotlin
    kotlin("plugin.spring")
    // org.springframework.boot は dependencyManagement で参照する為だけに必要で、
    // plugin としての apply は不要。クラスパスへの依存は build.gradle.kts で宣言済み。
    // なお、ここで apply false で宣言していても何故か bootJar などのタスクが作られてしまう…。
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
    }
}
