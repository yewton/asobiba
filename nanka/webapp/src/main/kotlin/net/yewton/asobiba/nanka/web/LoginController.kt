package net.yewton.asobiba.nanka.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/login")
@Suppress("FunctionOnlyReturningConstant")
class LoginController {
    @GetMapping
    fun login() = "redirect:/oauth2/authorization/github"
}
