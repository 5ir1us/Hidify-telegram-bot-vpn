package data.dto.payment

import kotlinx.serialization.Serializable

@Serializable
data class MetadataDto(
    val telegram_id: String // Telegram ID пользователя
)