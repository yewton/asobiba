package net.yewton.asobiba.nanka.web

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import java.time.LocalDateTime

class MyOAuth2AuthenticationSuccessHandler : AuthenticationSuccessHandler {

    private val delegate = SavedRequestAwareAuthenticationSuccessHandler()

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        LastLoggedInTimeHolder(request.session).store(LocalDateTime.now())
        delegate.onAuthenticationSuccess(request, response, authentication)
    }
}
