package net.yewton.asobiba.batista

import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.reactive.server.WebTestClientBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.Duration
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("ct")
class AppContainerTest(val webClient: WebTestClient) : StringSpec({
    beforeEach {
        listOf("nanya", "kanya").forEach {
            webClient.delete().uri(it)
                .exchange()
                .expectStatus().isAccepted
        }
    }

    "パスによってDataSourceがよしなに変わる" {
        listOf("nanya", "kanya").forEach { target ->
            val name = UUID.randomUUID().toString()
            webClient.run {
                post().uri("/$target?name={name}", name).exchange().expectStatus().isCreated

                get().uri("/$target?name={name}", name)
                    .exchange().expectStatus().isOk
                    .expectBody().jsonPath("$[0].name").isEqualTo(name)
            }
        }
    }
}) {

    @TestConfiguration
    @Suppress("MagicNumber")
    class Configuration {

        @Bean
        fun webTestClientCustomizer() = WebTestClientBuilderCustomizer { builder ->
            builder.responseTimeout(Duration.ofSeconds(30))
        }
    }
    override fun extensions() = listOf(SpringExtension)
}
