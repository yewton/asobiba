package net.yewton.asobiba.nanka.web

import jakarta.servlet.http.HttpServletRequest
import net.yewton.asobiba.spring.boot.common.Logging
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute

@Controller
class IndexController : Logging {

    @GetMapping("/")
    fun getIndex(model: Model): String {
        model.addAttribute("nanka", Nanka("ProductionHoge", "ProductionFuga"))
        return "index"
    }

    @ModelAttribute
    fun csrfToken(request: HttpServletRequest) =
        (request.getAttribute("_csrf") as? CsrfToken)?.let {
            Csrf(it.headerName, it.token)
        }
}
