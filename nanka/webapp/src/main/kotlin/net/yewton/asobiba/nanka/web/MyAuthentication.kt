package net.yewton.asobiba.nanka.web

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

/**
 * 初訪問時は [delegate] が null になり得る。
 */
class MyAuthentication(private val delegate: Authentication?) {
    private val authenticationTrustResolver = MyAuthenticationTrustResolver()

    fun isFullyAuthenticated() = authenticationTrustResolver.isFullyAuthenticated(delegate)

    companion object {
        fun current() = MyAuthentication(SecurityContextHolder.getContext().authentication)
    }
}
