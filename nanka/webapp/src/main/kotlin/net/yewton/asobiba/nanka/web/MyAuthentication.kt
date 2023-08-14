package net.yewton.asobiba.nanka.web

import org.springframework.security.authentication.AuthenticationTrustResolverImpl
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

class MyAuthentication(private val delegate: Authentication) {
    private val authenticationTrustResolver = AuthenticationTrustResolverImpl()

    fun isFullyAuthenticated() = authenticationTrustResolver.isFullyAuthenticated(delegate)

    companion object {
        fun current() = MyAuthentication(SecurityContextHolder.getContext().authentication)
    }
}
