package net.yewton.asobiba.nanka.web

import net.yewton.asobiba.spring.boot.common.Logging
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController : Logging {

    @GetMapping("/")
    fun getIndex(model: Model): String {
        model.addAttribute("nanka", Nanka("ProductionHoge", "ProductionFuga"))
        return "index"
    }
}
