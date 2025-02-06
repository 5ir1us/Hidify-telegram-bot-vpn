package data.dto.payment

import kotlinx.serialization.Serializable

@Serializable
data class PaymentMethodDto(
    val card: CardDto,
    val id: String,
    val saved: Boolean,
    val title: String,
    val type: String
)