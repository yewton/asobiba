package net.yewton.asobiba.nanka.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/my")
class MyController {

    @GetMapping
    @Suppress("FunctionOnlyReturningConstant")
    fun get() = "my"

    @GetMapping("/sensitive")
    @Suppress("FunctionOnlyReturningConstant")
    fun getSensitive() = "sensitive"
}
