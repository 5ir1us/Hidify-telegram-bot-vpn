package data.dto.payment.webhook

import data.dto.payment.AmountDto
import kotlinx.serialization.Serializable

@Serializable
data class PaymentWebhookObjectDto(
    val id: String,
    val status: String,
    val description: String? = null,
    val amount: AmountDto
)
