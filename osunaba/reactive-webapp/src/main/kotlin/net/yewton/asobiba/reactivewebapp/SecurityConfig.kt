package net.yewton.asobiba.reactivewebapp

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.invoke
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {
    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain = http {
        csrf {
            disable()
            requireCsrfProtectionMatcher = NegatedServerWebExchangeMatcher(
                OrServerWebExchangeMatcher(
                    PathPatternParserServerWebExchangeMatcher("/things"),
                    PathPatternParserServerWebExchangeMatcher("/things/**")
                )
            )
        }
        authorizeExchange {
            authorize("/**", permitAll)
        }
    }
}
