package net.yewton.asobiba.batista.model

import java.time.LocalDateTime

data class Kanya(
    val id: Long,
    val name: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun newInstance(name: String): Kanya {
            val now = LocalDateTime.now()
            return Kanya(0, name, now, now)
        }
    }
}
