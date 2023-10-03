package net.yewton.asobiba.nanka.web.unsafe.rememberme

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.yewton.asobiba.nanka.web.MyRememberMeUser
import net.yewton.asobiba.nanka.web.MyUser
import org.springframework.security.authentication.AuthenticationDetailsSource
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.RememberMeServices
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.security.web.authentication.logout.LogoutHandler
import java.time.Duration

class MyUnsafeRememberMeServices : RememberMeServices, LogoutHandler {

    private val authenticationDetailsSource: AuthenticationDetailsSource<HttpServletRequest, *> =
        WebAuthenticationDetailsSource()

    companion object {
        const val COOKIE_NAME = "unsafe-remember-me"
        const val MAX_AGE_DAYS = 14L
    }

    /**
     * Remember-me 以外でログイン成功した場合に呼ばれる。
     * Remember-me 認証に必要な情報を保存する。
     */
    override fun loginSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        successfulAuthentication: Authentication
    ) {
        val user = successfulAuthentication.principal as? MyUser ?: return
        val username = user.getUsername()
        val cookie = Cookie(COOKIE_NAME, username)
        cookie.maxAge = Duration.ofDays(MAX_AGE_DAYS).toSeconds().toInt()
        cookie.path = getCookiePath(request)
        cookie.secure = request.isSecure
        cookie.isHttpOnly = true
        response.addCookie(cookie)
    }

    private fun getCookiePath(request: HttpServletRequest): String {
        val contextPath = request.contextPath
        return contextPath.ifEmpty { "/" }
    }
    override fun autoLogin(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication? {
        val username = extractRememberMeCookie(request)
        username ?: return null

        val userDetails = MyRememberMeUser(User.withUsername(username).password("").build())
        return createSuccessfulAuthentication(request, userDetails)
    }

    override fun loginFail(request: HttpServletRequest, response: HttpServletResponse) {
        cancelCookie(request, response)
    }

    override fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        cancelCookie(request, response)
    }

    private fun extractRememberMeCookie(request: HttpServletRequest): String? {
        val cookies = request.cookies
        return cookies?.firstOrNull { COOKIE_NAME == it.name }?.value
    }

    private fun createSuccessfulAuthentication(
        request: HttpServletRequest,
        user: UserDetails
    ): Authentication {
        val auth = MyUnsafeRememberMeAuthenticationToken(
            user,
            AuthorityUtils.NO_AUTHORITIES
        )
        auth.details = this.authenticationDetailsSource.buildDetails(request)
        return auth
    }

    private fun cancelCookie(
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        val cookie = Cookie(COOKIE_NAME, null)
        cookie.maxAge = 0
        cookie.path = getCookiePath(request)
        cookie.secure = request.isSecure
        response.addCookie(cookie)
    }
}
