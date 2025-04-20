package data.dto.payment.webhook

import kotlinx.serialization.Serializable

/**
 * @param event Событие, которое вы хотите отслеживать.
 * @param id Идентификатор webhook
 * @param url URL, на который ЮKassa будет отправлять уведомления.
 */
@Serializable
data class WebhookDto(
    val event: String,
    val id: String?=null,
    val url: String
)