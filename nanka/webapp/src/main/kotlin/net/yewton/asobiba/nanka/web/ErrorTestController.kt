package net.yewton.asobiba.nanka.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/errortest")
class ErrorTestController {

    @GetMapping("exhandler")
    fun exhandler(): String {
        throw NormalError()
    }

    @GetMapping("rethrow-teapot")
    fun teapot(): String {
        throw TeaPotError()
    }

    @GetMapping("rethrow-bad")
    fun rethrow(): String {
        throw MoreSpecificBadRequestError()
    }

    @GetMapping("bad")
    fun bad(): String {
        throw BadRequestError()
    }
}
