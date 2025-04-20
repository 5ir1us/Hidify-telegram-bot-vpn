package domain.model

data class Webhook(
    val event: String,
    val id: String?=null,
    val url: String
)
