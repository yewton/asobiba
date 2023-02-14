plugins {
    id("net.yewton.asobiba.kotlin-spring-boot-library")
    id("net.yewton.asobiba.optional-dependencies")
}

group = "$group.spring-boot-libs"

// https://docs.spring.io/spring-boot/docs/3.0.2/reference/htmlsingle/#features.developing-auto-configuration
dependencies {
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    annotationProcessor("org.springframework.boot:spring-boot-autoconfigure-processor")
    optional("org.springframework.boot:spring-boot-devtools")
    optional("org.springframework.boot:spring-boot-starter-thymeleaf")
}
