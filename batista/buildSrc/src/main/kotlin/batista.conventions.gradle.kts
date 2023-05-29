plugins {
    java
    `jvm-test-suite`
}

// 実際のDBに繋いでテストしたく、 build 時は実行してほしくないので新しく定義する
val integrationTest: SourceSet by sourceSets.creating {
    compileClasspath += sourceSets.main.get().output
    runtimeClasspath += sourceSets.main.get().output
}

val integrationTestImplementation by configurations.getting {
    extendsFrom(configurations.testImplementation.get())
}
configurations[integrationTest.runtimeOnlyConfigurationName].extendsFrom(configurations.testRuntimeOnly.get())

dependencies {
    implementation("net.yewton.asobiba.spring-boot-libs:commons")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter")
    // Platform で指定されているから？↓で指定されているバージョンより上の Spring Boot が落ちてくる。正しい。
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("io.kotest:kotest-assertions-core")
    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest.extensions:kotest-extensions-spring")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.2")
}

tasks.register<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = integrationTest.output.classesDirs
    classpath = integrationTest.runtimeClasspath

    useJUnitPlatform()
}
