package net.yewton.asobiba.nanka.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository
import java.util.function.Consumer

@Configuration
class SecurityConfig(private val customClientRegistrationRepository: ClientRegistrationRepository) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeRequests {
                listOf("/", "/error", "/assets/**", "/front/**", "/js/**").forEach {
                    authorize(it, permitAll)
                }
                authorize(anyRequest, authenticated)
            }
            exceptionHandling {
                authenticationEntryPoint = HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
            }
            csrf {
                csrfTokenRepository = HttpSessionCsrfTokenRepository()
            }
            logout {
                logoutSuccessUrl = "/"
                permitAll()
            }
            oauth2Login {
                // https://docs.spring.io/spring-security/reference/6.0.1/servlet/oauth2/client/authorization-grants.html
                authorizationEndpoint {
                    authorizationRequestResolver = authorizationRequestResolver(customClientRegistrationRepository)
                }
            }
        }
        return http.build()
    }

    private fun authorizationRequestResolver(
        clientRegistrationRepository: ClientRegistrationRepository?
    ): OAuth2AuthorizationRequestResolver {
        // https://github.com/spring-projects/spring-security/blob/6.0.1/config/src/main/java/org/springframework/security/config/http/OAuth2LoginBeanDefinitionParser.java#L78
        val authorizationRequestResolver = DefaultOAuth2AuthorizationRequestResolver(
            clientRegistrationRepository,
            "/oauth2/authorization"
        )
        authorizationRequestResolver.setAuthorizationRequestCustomizer(
            authorizationRequestCustomizer()
        )
        return authorizationRequestResolver
    }

    private fun authorizationRequestCustomizer(): Consumer<OAuth2AuthorizationRequest.Builder> =
        Consumer {
            it.additionalParameters {
                    // https://stackoverflow.com/a/74243827/2142831
                    params ->
                params["prompt"] = "consent"
            }
        }
}
