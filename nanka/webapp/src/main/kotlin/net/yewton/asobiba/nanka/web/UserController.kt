package net.yewton.asobiba.nanka.web

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController {
    @GetMapping("/")
    fun user(@AuthenticationPrincipal principal: OAuth2User) =
        mapOf("name" to principal.getAttribute<String>("name"))
}
