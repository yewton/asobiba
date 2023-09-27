package net.yewton.asobiba.nanka.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class PreviewController {

    @GetMapping("/")
    fun getIndex(): ModelAndView {
        val mavBuilder = IndexModelAndViewBuilder().apply {
            nanka = Nanka("Hoge", "Fuga")
        }
        return mavBuilder.build()
    }

    @GetMapping("/my")
    @Suppress("FunctionOnlyReturningConstant")
    fun getMy() = "my"
}
