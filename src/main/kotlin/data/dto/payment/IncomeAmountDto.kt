package data.dto.payment

import kotlinx.serialization.Serializable

@Serializable
data class IncomeAmountDto(
    val currency: String,
    val value: String
)