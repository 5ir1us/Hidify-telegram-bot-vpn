package data.dto.payment

import kotlinx.serialization.Serializable

@Serializable
data class AmountDto(
    val currency: String,
    val value: String
)