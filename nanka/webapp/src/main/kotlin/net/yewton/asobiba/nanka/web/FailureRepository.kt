package net.yewton.asobiba.nanka.web

import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import io.github.resilience4j.retry.annotation.Retry
import net.yewton.asobiba.spring.boot.common.Logging
import net.yewton.asobiba.spring.boot.common.Logging.Extensions.logger
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Component
class FailureRepository(webClientBuilder: WebClient.Builder) : Logging {

    private val webClient: WebClient = webClientBuilder.baseUrl("https://httpbin.org/")
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

    @CircuitBreaker(name = "failure", fallbackMethod = "fallback")
    @Retry(name = "failure")
    fun serverError(): Mono<String> {
        return webClient.get().uri("status/500")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono()
    }

    @Suppress("unused")
    private fun fallback(e: CallNotPermittedException): Mono<String> =
        Mono.just("fallback: $e")
}
