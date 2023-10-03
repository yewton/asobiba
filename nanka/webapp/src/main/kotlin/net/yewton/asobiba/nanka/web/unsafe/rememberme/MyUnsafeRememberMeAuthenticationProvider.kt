package net.yewton.asobiba.nanka.web.unsafe.rememberme

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import kotlin.reflect.full.isSubclassOf

class MyUnsafeRememberMeAuthenticationProvider : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication? {
        if (!supports(authentication::class.java)) {
            return null
        }
        return authentication
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication.kotlin.isSubclassOf(MyUnsafeRememberMeAuthenticationToken::class)
    }
}
