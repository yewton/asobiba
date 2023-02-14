plugins {
    id("net.yewton.asobiba.kotlin-spring-boot-application")
    id("net.yewton.asobiba.spotless-node")
    id("net.yewton.asobiba.vite")
}

group = "$group.nanka"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation("net.yewton.asobiba.spring-boot-libs:commons")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation(project(":view"))
    developmentOnly("net.yewton.asobiba.spring-boot-libs:devtools")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

configure<net.yewton.asobiba.gradle.VitePluginExtension> {
    sourceDir.set(file("src/main/ts"))
    outputsDir.set(file("src/main/resources/static/js"))
}
