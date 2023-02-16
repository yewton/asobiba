package net.yewton.asobiba.nanka.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PreviewController {

    @GetMapping("/")
    fun getIndex(model: Model): String {
        model.addAttribute("nanka", Nanka("Hoge", "Fuga"))
        return "top"
    }

    @GetMapping("/my")
    @Suppress("FunctionOnlyReturningConstant")
    fun getMy() = "my"
}
