package net.yewton.asobiba.batista.mapper.second

import net.yewton.asobiba.batista.model.Nanya
import org.apache.ibatis.annotations.Mapper

@Mapper
interface NanyaMapper {
    fun findByName(name: String): List<Nanya>

    fun insert(nanya: Nanya)

    fun truncate()

    fun all(): List<Nanya>
}
