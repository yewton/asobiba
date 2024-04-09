package net.yewton.asobiba.nanka.web

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.cache.CacheManager
import org.springframework.cache.get
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class RequestScopedCacheInvalidationFilter(val cacheManager: CacheManager) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        filterChain.doFilter(request, response)

        cacheManager["request"]?.invalidate()
    }
}
