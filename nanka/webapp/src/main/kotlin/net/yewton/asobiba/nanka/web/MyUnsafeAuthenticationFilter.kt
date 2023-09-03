package net.yewton.asobiba.nanka.web

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.ApplicationEventPublisherAware
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

/**
 * ↓を参考にでっちあげ認証フィルタを実装してみる
 * https://github.com/spring-projects/spring-security/blob/6.1.3/web/src/main/java/org/springframework/security/web/authentication/rememberme/RememberMeAuthenticationFilter.java
 *
 * EventPublisher をもらう為に Component 宣言している。
 * 二重に登録されないように SecurityConfig で回避設定を入れている。
 */
@Component
class MyUnsafeAuthenticationFilter : OncePerRequestFilter(), ApplicationEventPublisherAware {
    companion object {
        private val pattern = Regex("UNSAFE-AUTH: (.+)$")
    }

    private var eventPublisher: ApplicationEventPublisher? = null
    private val securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy()
    private val securityContextRepository = HttpSessionSecurityContextRepository()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        if (this.securityContextHolderStrategy.context.authentication != null) {
            chain.doFilter(request, response)
            return
        }
        val userAgent = request.getHeader("User-Agent") ?: ""
        val username = pattern.find(userAgent)?.let {
            it.groups[1]?.value
        } ?: ""
        if (username.isNotEmpty()) {
            val context = this.securityContextHolderStrategy.createEmptyContext()
            context.authentication = MyUnsafeAuthenticationToken(
                MyUnsafeAuthUser(User.withUsername(username).password("").build()),
                AuthorityUtils.NO_AUTHORITIES
            )
            this.securityContextHolderStrategy.context = context
            this.securityContextRepository.saveContext(context, request, response)
            eventPublisher?.publishEvent(
                InteractiveAuthenticationSuccessEvent(
                    securityContextHolderStrategy.context.authentication,
                    this.javaClass
                )
            )
        }
        chain.doFilter(request, response)
    }

    override fun setApplicationEventPublisher(applicationEventPublisher: ApplicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher
    }
}
