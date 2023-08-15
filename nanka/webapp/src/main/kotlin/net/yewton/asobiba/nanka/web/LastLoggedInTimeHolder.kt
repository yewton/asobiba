package net.yewton.asobiba.nanka.web

import jakarta.servlet.http.HttpSession
import java.time.LocalDateTime

class LastLoggedInTimeHolder(private val session: HttpSession) {
    companion object {
        const val ATTR_NAME = "LAST_LOGGED_IN_AT"
    }

    fun store(time: LocalDateTime) {
        session.setAttribute(ATTR_NAME, time)
    }

    fun get(): LocalDateTime? = session.getAttribute(ATTR_NAME) as? LocalDateTime
}
