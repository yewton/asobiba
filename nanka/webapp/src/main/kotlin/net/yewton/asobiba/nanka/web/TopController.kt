package net.yewton.asobiba.nanka.web

import net.yewton.asobiba.spring.boot.common.Logging
import net.yewton.asobiba.spring.boot.common.Logging.Extensions.logger
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class TopController(val textsRepository: TextsRepository) : Logging {

    @GetMapping("/")
    fun get(): ModelAndView {
        val mavBuilder = IndexModelAndViewBuilder().apply {
            nanka = Nanka("ProductionHoge", "ProductionFuga")
        }
        logger.info("cache1 {}", textsRepository.listFromMainCache().block())
        logger.info("cache2 {}", textsRepository.listFromMainCache().block())
        logger.info("request cache1 {}", textsRepository.listFromRequestCache().block())
        logger.info("request cache2 {}", textsRepository.listFromRequestCache().block())
        return mavBuilder.build()
    }
}
