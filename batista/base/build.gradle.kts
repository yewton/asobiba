plugins {
    id("net.yewton.asobiba.kotlin-spring-boot-library")
    id("batista.conventions")
    kotlin("kapt")
}

dependencies {
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    kapt("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.compileJava {
    inputs.files(tasks.processResources)
}
