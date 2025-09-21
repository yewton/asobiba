group = "$group.osunaba"

plugins {
    id("net.yewton.asobiba.kotlin-spring-boot-application")
}

dependencies {
    implementation(platform("org.springframework.ai:spring-ai-bom:1.0.2"))
    implementation("org.springframework:spring-web")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework.ai:spring-ai-starter-mcp-client")
    implementation("org.springframework.ai:spring-ai-starter-model-anthropic")
    implementation("org.springframework.ai:spring-ai-starter-model-vertex-ai-gemini")
    implementation("org.springframework.ai:spring-ai-starter-model-openai")
}
