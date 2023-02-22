package net.yewton.asobiba.batista.mapper.first

import net.yewton.asobiba.batista.model.Kanya
import org.apache.ibatis.annotations.Mapper

@Mapper
interface KanyaMapper {
    fun findByName(name: String): List<Kanya>

    fun insert(kanya: Kanya)

    fun truncate()

    fun all(): List<Kanya>
}
