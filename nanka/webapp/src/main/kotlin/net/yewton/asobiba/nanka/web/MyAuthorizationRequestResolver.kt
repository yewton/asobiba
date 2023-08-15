package net.yewton.asobiba.nanka.web

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest

class MyAuthorizationRequestResolver(
    private val delegate: DefaultOAuth2AuthorizationRequestResolver
) : OAuth2AuthorizationRequestResolver {

    override fun resolve(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        return decorate(request) { delegate.resolve(it) }
    }

    override fun resolve(
        request: HttpServletRequest,
        clientRegistrationId: String
    ): OAuth2AuthorizationRequest? {
        return decorate(request) { delegate.resolve(it, clientRegistrationId) }
    }

    private fun decorate(
        request: HttpServletRequest,
        provider: (HttpServletRequest) -> OAuth2AuthorizationRequest?
    ): OAuth2AuthorizationRequest? {
        val base = provider.invoke(request) ?: return null
        val reAuthRequirementChecker = ReAuthRequirementChecker(
            MyAuthentication.current(),
            LastLoggedInTimeHolder(request.session)
        )
        val builder = OAuth2AuthorizationRequest.from(base)
        if (reAuthRequirementChecker.reAuthRequired()) {
            builder.additionalParameters { params -> params["prompt"] = "consent" }
        }
        return builder.build()
    }
}
