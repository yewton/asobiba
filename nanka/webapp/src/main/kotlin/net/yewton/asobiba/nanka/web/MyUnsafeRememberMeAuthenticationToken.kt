package net.yewton.asobiba.nanka.web

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class MyUnsafeRememberMeAuthenticationToken(
    private val principal: Any,
    authorities: Collection<GrantedAuthority?>
) :
    AbstractAuthenticationToken(authorities) {

    init {
        isAuthenticated = true
    }

    override fun getCredentials() = ""

    override fun getPrincipal() = principal
}
