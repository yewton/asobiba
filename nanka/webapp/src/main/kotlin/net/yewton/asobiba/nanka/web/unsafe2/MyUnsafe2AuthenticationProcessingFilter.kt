package net.yewton.asobiba.nanka.web.unsafe2

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

class MyUnsafe2AuthenticationProcessingFilter(authenticationManager: AuthenticationManager) :
    AbstractAuthenticationProcessingFilter(AntPathRequestMatcher("/login2"), authenticationManager) {

    init {
        setAuthenticationDetailsSource(WebAuthenticationDetailsSource())
    }

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {
        val username = request.getParameter("username")
        val password = request.getParameter("password")
        val auth = UsernamePasswordAuthenticationToken(username, password)
        auth.details = this.authenticationDetailsSource.buildDetails(request)
        return this.authenticationManager.authenticate(auth)
    }
}
