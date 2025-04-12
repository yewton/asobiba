group = "$group.osunaba"

plugins {
    id("net.yewton.asobiba.kotlin-spring-boot-application")
}

springBoot {
    mainClass = "net.yewton.asobiba.mcp.server.McpServerApplicationKt"
}

dependencies {
    implementation(platform("org.springframework.ai:spring-ai-bom:1.0.0-M7"))
    implementation("org.springframework:spring-web")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework.ai:spring-ai-starter-mcp-server")
}
