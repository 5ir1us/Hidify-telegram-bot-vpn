package data.dto.payment.webhook

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WebhookPaymentEventDto(
    val type: String,
    val event: String,
    @SerialName("object") val payment: PaymentWebhookObjectDto
)
