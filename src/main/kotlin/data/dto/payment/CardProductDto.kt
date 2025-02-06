package data.dto.payment

import kotlinx.serialization.Serializable

@Serializable
data class CardProductDto(
    val code: String,
    val name: String
)