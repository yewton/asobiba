package net.yewton.asobiba.nanka.web

import net.yewton.asobiba.spring.boot.common.Logging
import net.yewton.asobiba.spring.boot.common.Logging.Extensions.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class ApiController(val failureRepository: FailureRepository) : Logging {

    @GetMapping("cb")
    fun cb(): String {
        logger.info("failure {}", failureRepository.serverError().onErrorReturn("onError").block())
        return "ok"
    }
}
