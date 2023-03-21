package net.yewton.asobiba.reactivewebapp

import com.fasterxml.jackson.annotation.JsonProperty
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
    @Schema(
        description =
        // language=markdown
        """
説明文等を書く為に `@Schema` を使ってしまうと、
Kotlin の Nullability を使った `required` の自動判定が効かなくなるみたい？
なので、厳密に表明するには `requiredMode` を設定する必要があるっぽい。
        """,
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    val name: String,
    private val sharedFieldJava: String = "デフォルト値",
    override val sharedFieldKotlin: String = "デフォルト値"
) : JavaInterface, KotlinInterface {

    // https://youtrack.jetbrains.com/issue/KT-6653
    @JsonProperty
    override fun sharedFieldJava() = sharedFieldJava
}
