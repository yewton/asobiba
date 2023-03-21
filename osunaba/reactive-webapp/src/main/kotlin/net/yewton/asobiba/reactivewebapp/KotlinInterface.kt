package net.yewton.asobiba.reactivewebapp

import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode

interface KotlinInterface {
    @get:Schema(description = "Kotlinインタフェースで宣言された共有フィールドです", requiredMode = RequiredMode.REQUIRED)
    val sharedFieldKotlin: String
}
