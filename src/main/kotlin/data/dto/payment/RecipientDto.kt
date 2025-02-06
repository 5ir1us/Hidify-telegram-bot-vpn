package data.dto.payment

import kotlinx.serialization.Serializable

@Serializable
data class RecipientDto(
    val account_id: String,
    val gateway_id: String
)