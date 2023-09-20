plugins {
    id("net.yewton.asobiba.kotlin-spring-boot-application")
    id("batista.conventions")
}

dependencies {
    implementation(project(":base"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.compileJava {
    inputs.files(tasks.processResources)
}

tasks.processContainerTestResources {
    from("$projectDir/../../database/db1_schema.sql")
    from("$projectDir/../../database/db2_schema.sql")
}
