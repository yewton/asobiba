package net.yewton.asobiba.batista

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import net.yewton.asobiba.spring.boot.common.Logging
import net.yewton.asobiba.spring.boot.common.Logging.Extensions.logger
import org.springframework.stereotype.Component

@Component
class DataSourceRoutingFilter : Filter, Logging {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        (request as? HttpServletRequest)?.let {
            if (request.requestURI.contains("kanya")) {
                DataSourceContextHolder.set(MyDataSource.FIRST)
            } else if (request.requestURI.contains("nanya")) {
                DataSourceContextHolder.set(MyDataSource.SECOND)
            }
        }
        logger.info("データソースコンテキスト: {}", DataSourceContextHolder.get())
        chain.doFilter(request, response)
        DataSourceContextHolder.clear()
    }
}
