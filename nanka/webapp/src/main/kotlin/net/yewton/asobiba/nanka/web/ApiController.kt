package net.yewton.asobiba.nanka.web

import net.yewton.asobiba.spring.boot.common.Logging
import net.yewton.asobiba.spring.boot.common.Logging.Extensions.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api")
class ApiController(val failureRepository: FailureRepository, val textsRepository: TextsRepository) : Logging {

    @GetMapping("cb")
    fun cb(): String {
        logger.info("failure {}", failureRepository.serverError().onErrorReturn("onError").block())
        return "ok"
    }

    @GetMapping("mono-hot")
    @Suppress("MagicNumber")
    fun monoHot(): String? {
        val proc = { mono: Mono<*>, prefix: String ->
            mono.doOnSubscribe { logger.info("*** $prefix のリクエストが始まったよ") }
                .doOnSuccess { logger.info("*** $prefix のリクエストが終わったよ") }
                .subscribe()
        }
        val texts1 = textsRepository.list().cache().also { proc(it, "最初") }
        val texts2 = textsRepository.list().cache().also { proc(it, "２番目") }
        val texts3 = textsRepository.list().cache().also { proc(it, "さいご") }
        logger.info("--- API リクエストはこの時点ですべて発火しているよ。しばらく待つよ。")
        Thread.sleep(10000)
        logger.info("--- しばらく待ったよ。もう全てのリクエストが終わっているはずだよ。")
        return texts1.flatMap { t1 ->
            texts2.flatMap { t2 ->
                texts3.map { t3 ->
                    "${t1.data.first().title}, ${t2.data.first().author} ${t3.data.first().content}"
                }
            }
        }.block()
    }
}
