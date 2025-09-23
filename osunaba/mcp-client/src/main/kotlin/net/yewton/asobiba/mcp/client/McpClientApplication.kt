/*
 * Copyright 2025-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.yewton.asobiba.mcp.client

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.MessageWindowChatMemory
import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean

@SpringBootApplication
class McpClientApplication {
    @Value("\${ai.user.input}")
    private val userInput: String = ""

    @Bean
    fun predefinedQuestions(
        chatClientBuilder: ChatClient.Builder,
        tools: ToolCallbackProvider,
        context: ConfigurableApplicationContext
    ): CommandLineRunner {
        return CommandLineRunner { _ ->
            val chatClient = chatClientBuilder
                .defaultSystem(
                    """
                    あなたは、ユーザーのリクエストに基づいて、ツールを用いて情報を取得してユーザーを支援するアシスタントです。
                    ユーザーのコメントから判断し、もし利用可能なツールがある場合は、
                    ツールを利用して取得した内容をわかりやすい表現や出力形式にした上でユーザーに提供してください。
                    回答には利用されたツールの情報も含めてください。
                    もし利用可能なツールがない場合は、その旨を伝えながら自然に回答してください。
                    ユーザーが使用する言語で回答するようにしてください。
                    """.trimIndent()
                )
                .defaultTools(tools)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(MessageWindowChatMemory.builder().build()).build())
                .build()
            println("\n>>> QUESTION: $userInput")
            println("\n>>> ASSISTANT: ${chatClient.prompt(userInput).call().content()}")
            context.close()
        }
    }
}

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<McpClientApplication>(*args)
}
