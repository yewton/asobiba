package net.yewton.asobiba.nanka.web

import org.springframework.security.authentication.AuthenticationTrustResolver
import org.springframework.security.authentication.AuthenticationTrustResolverImpl
import org.springframework.security.core.Authentication
import kotlin.reflect.full.isSubclassOf

class MyAuthenticationTrustResolver(
    private val delegate: AuthenticationTrustResolverImpl = AuthenticationTrustResolverImpl()
) :
    AuthenticationTrustResolver by delegate {

    override fun isRememberMe(authentication: Authentication?): Boolean {
        return delegate.isRememberMe(authentication) || authentication?.let {
            it::class.isSubclassOf(MyUnsafeRememberMeAuthenticationToken::class)
        } ?: false
    }
}
