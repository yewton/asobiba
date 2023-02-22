package net.yewton.asobiba.batista.model

import java.time.LocalDateTime

data class Nanya(
    val id: Long,
    val name: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun newInstance(name: String): Nanya {
            val now = LocalDateTime.now()
            return Nanya(0, name, now, now)
        }
    }
}
