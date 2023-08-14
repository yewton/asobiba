package net.yewton.asobiba.nanka.web

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest

class MyAuthorizationRequestResolver(
    private val delegate: DefaultOAuth2AuthorizationRequestResolver
) : OAuth2AuthorizationRequestResolver {

    override fun resolve(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        return decorate(delegate.resolve(request))
    }

    override fun resolve(
        request: HttpServletRequest,
        clientRegistrationId: String
    ): OAuth2AuthorizationRequest? {
        return decorate(delegate.resolve(request, clientRegistrationId))
    }

    private fun decorate(base: OAuth2AuthorizationRequest?): OAuth2AuthorizationRequest? {
        return base?.let {
            val builder = OAuth2AuthorizationRequest.from(it)
            if (!MyAuthentication.current().isFullyAuthenticated()) {
                builder.additionalParameters { params -> params["prompt"] = "consent" }
            }
            builder.build()
        }
    }
}
