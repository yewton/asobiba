package net.yewton.asobiba.nanka.web

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class ReAuthRequirementChecker(
    private val authentication: MyAuthentication,
    private val lastLoggedInTimeHolder: LastLoggedInTimeHolder
) {
    companion object {
        const val EXPIRES = 30
    }

    fun reAuthRequired(): Boolean {
        if (!authentication.isFullyAuthenticated()) {
            return true
        }
        return lastLoggedInTimeHolder.get()?.let {
            EXPIRES < ChronoUnit.SECONDS.between(it, LocalDateTime.now())
        } ?: true
    }
}
