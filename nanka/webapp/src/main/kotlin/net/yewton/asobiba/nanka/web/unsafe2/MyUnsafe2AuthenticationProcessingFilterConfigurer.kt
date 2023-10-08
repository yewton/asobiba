package net.yewton.asobiba.nanka.web.unsafe2

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy

class MyUnsafe2AuthenticationProcessingFilterConfigurer :
    AbstractHttpConfigurer<MyUnsafe2AuthenticationProcessingFilterConfigurer, HttpSecurity>() {
    override fun configure(http: HttpSecurity) {
        // ↓を参考にしてみたものの、そもそもおいそれと AuthenticationProcessingFilter を拡張しない方がよさそう
        // https://github.com/spring-projects/spring-security/blob/6.1.4/config/src/main/java/org/springframework/security/config/annotation/web/configurers/AbstractAuthenticationFilterConfigurer.java#L269
        val authenticationManager = http.getSharedObject(AuthenticationManager::class.java)
        val sessionAuthenticationStrategy = http.getSharedObject(SessionAuthenticationStrategy::class.java)
        val rememberMeServices = http.getSharedObject(RememberMeServices::class.java)

        http {
            addFilterBefore<AnonymousAuthenticationFilter>(
                MyUnsafe2AuthenticationProcessingFilter(authenticationManager).apply {
                    setSessionAuthenticationStrategy(sessionAuthenticationStrategy)
                    setRememberMeServices(rememberMeServices)
                    setSecurityContextHolderStrategy(getSecurityContextHolderStrategy())
                }
            )
        }
    }
}
