package net.yewton.asobiba.reactivewebapp

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table
@Schema(description = "何かのモノです")
data class Thing(
    @Id
    @Schema(description = "識別子")
    val id: UUID?,
    @Schema(description = "名称")
    val name: String
)
