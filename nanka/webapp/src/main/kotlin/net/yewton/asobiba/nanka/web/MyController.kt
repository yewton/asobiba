package net.yewton.asobiba.nanka.web

import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/my")
@Suppress("FunctionOnlyReturningConstant")
class MyController {

    @GetMapping
    fun get() = "my"

    @GetMapping("/sensitive")
    fun getSensitive() = "sensitive"

    @GetMapping("/sensitive2")
    fun getSensitive2(): String {
        if (!MyAuthentication.current().isFullyAuthenticated()) {
            throw InsufficientAuthenticationException("ちゃんとログインしてね！")
        }
        return "sensitive2"
    }
}
