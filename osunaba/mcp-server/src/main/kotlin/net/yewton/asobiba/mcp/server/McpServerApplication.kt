package net.yewton.asobiba.mcp.server

import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class McpServerApplication {
    @Bean
    fun weatherTools(weatherService: WeatherService): ToolCallbackProvider {
        return MethodToolCallbackProvider.builder().toolObjects(weatherService).build()
    }
}

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<McpServerApplication>(*args)
}
