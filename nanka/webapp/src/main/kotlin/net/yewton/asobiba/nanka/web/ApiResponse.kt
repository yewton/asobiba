package net.yewton.asobiba.nanka.web

data class ApiResponse<T> (
    val status: String,
    val code: Int,
    val total: Long,
    val data: List<T>
)
