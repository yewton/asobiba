package net.yewton.asobiba.batista

import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.UUID

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppTest(val webClient: WebTestClient) : StringSpec({
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
    override fun extensions() = listOf(SpringExtension)
}
