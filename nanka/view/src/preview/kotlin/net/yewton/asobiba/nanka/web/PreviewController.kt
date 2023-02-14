package net.yewton.asobiba.nanka.web

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class PreviewController {

    @GetMapping("/")
    fun getIndex(model: Model): String {
        model.addAttribute("nanka", Nanka("Hoge", "Fuga"))
        return "index"
    }

    @ResponseBody
    @GetMapping("/user/")
    fun getUser() = mapOf("name" to "Test")

    @ModelAttribute
    fun csrf() = Csrf("CSRF_HEADER", "CSRF_TOKEN")
}
