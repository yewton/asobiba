package net.yewton.asobiba.nanka.web

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
}
