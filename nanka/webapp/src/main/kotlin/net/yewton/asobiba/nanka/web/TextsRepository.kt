package net.yewton.asobiba.nanka.web

import net.yewton.asobiba.spring.boot.common.Logging
import net.yewton.asobiba.spring.boot.common.Logging.Extensions.logger
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Component
class TextsRepository(webClientBuilder: WebClient.Builder) : Logging {

    data class Text(val title: String, val author: String, val genre: String, val content: String)

    private val webClient: WebClient = webClientBuilder.baseUrl("https://fakerapi.it/api/v1/")
        .filter(
            ExchangeFilterFunction.ofResponseProcessor { response ->
                logger.info(
                    "{} {}: {}",
                    response.request().method,
                    response.request().uri,
                    response.statusCode()
                )
                Mono.just(response)
            }
        )
        .build()

    @Cacheable(cacheNames = ["permanent"], key = "'texts'", sync = true)
    fun listFromMainCache() = list()

    @Cacheable(cacheNames = ["request"], key = "'texts'", sync = true)
    fun listFromRequestCache() = list()

    fun list(): Mono<ApiResponse<Text>> {
        return webClient.get().uri("texts?_quantity=5&_characters=30")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono()
    }
}
