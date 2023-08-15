package net.yewton.asobiba.nanka.web

import jakarta.servlet.http.HttpSession
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
    fun getSensitive2(session: HttpSession): String {
        val fullyAuthenticatedChecker = ReAuthRequirementChecker(
            MyAuthentication.current(),
            LastLoggedInTimeHolder(session)
        )
        if (fullyAuthenticatedChecker.reAuthRequired()) {
            throw InsufficientAuthenticationException("もう一度ログインしてね！")
        }
        return "sensitive2"
    }
}
