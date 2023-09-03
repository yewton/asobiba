package net.yewton.asobiba.nanka.web

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authorization.AuthenticatedAuthorizationManager
import org.springframework.security.config.annotation.ObjectPostProcessor
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.ExceptionTranslationFilter
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
class SecurityConfig(
    private val customClientRegistrationRepository: ClientRegistrationRepository,
    private val myUnsafeAuthenticationFilter: MyUnsafeAuthenticationFilter
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                listOf("/", "/login", "/error", "/assets/**", "/front/**", "/js/**").forEach {
                    authorize(it, permitAll)
                }
                authorize(
                    "/my/sensitive",
                    AuthenticatedAuthorizationManager.fullyAuthenticated<RequestAuthorizationContext>().apply {
                        setTrustResolver(MyAuthenticationTrustResolver())
                    }
                )
                authorize(anyRequest, authenticated)
            }
            exceptionHandling {
                defaultAuthenticationEntryPointFor(
                    HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                    AntPathRequestMatcher("/user/")
                )
                defaultAuthenticationEntryPointFor(
                    LoginUrlAuthenticationEntryPoint("/oauth2/authorization/github"),
                    AntPathRequestMatcher("/**")
                )
            }
            apply(ExceptionHandlingConfigurer()) {
                addObjectPostProcessor(object : ObjectPostProcessor<ExceptionTranslationFilter> {
                    override fun <O : ExceptionTranslationFilter> postProcess(filter: O) =
                        filter.apply {
                            setAuthenticationTrustResolver(MyAuthenticationTrustResolver())
                        }
                })
            }
            csrf {
                csrfTokenRepository = HttpSessionCsrfTokenRepository()
            }
            logout {
                logoutSuccessUrl = "/"
                permitAll()
            }
            oauth2Login {
                loginPage = "/login"
                // https://docs.spring.io/spring-security/reference/6.0.1/servlet/oauth2/client/authorization-grants.html
                authorizationEndpoint {
                    authorizationRequestResolver = authorizationRequestResolver(customClientRegistrationRepository)
                }
                userInfoEndpoint {
                    userService = oauth2UserService()
                }
                redirectionEndpoint {
                    baseUri = "/login/callback/*"
                }
                authenticationSuccessHandler = MyOAuth2AuthenticationSuccessHandler()
            }
            rememberMe {
                alwaysRemember = true
                useSecureCookie = true
                rememberMeServices = rememberMeServices()
            }
            addFilterBefore<AnonymousAuthenticationFilter>(myUnsafeAuthenticationFilter)
        }
        http.authenticationProvider(MyUnsafeRememberMeAuthenticationProvider())
        return http.build()
    }

    private fun authorizationRequestResolver(
        clientRegistrationRepository: ClientRegistrationRepository?
    ): OAuth2AuthorizationRequestResolver {
        // https://github.com/spring-projects/spring-security/blob/6.0.1/config/src/main/java/org/springframework/security/config/http/OAuth2LoginBeanDefinitionParser.java#L78
        return MyAuthorizationRequestResolver(
            DefaultOAuth2AuthorizationRequestResolver(
                clientRegistrationRepository,
                "/oauth2/authorization"
            )
        )
    }

    @Bean
    fun rememberMeServices() = MyUnsafeRememberMeServices()

    @Bean
    fun oauth2UserService(): OAuth2UserService<OAuth2UserRequest, OAuth2User> {
        val delegate = DefaultOAuth2UserService()
        return OAuth2UserService { userRequest ->
            MyOauth2User(delegate.loadUser(userRequest))
        }
    }

    // https://docs.spring.io/spring-security/reference/servlet/architecture.html#adding-custom-filter
    @Bean
    fun tenantFilterRegistration(filter: MyUnsafeAuthenticationFilter) =
        FilterRegistrationBean(filter).apply {
            setEnabled(false)
        }
}
