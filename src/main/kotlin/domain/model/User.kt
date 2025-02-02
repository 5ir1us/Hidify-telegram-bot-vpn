package domain.model

data class User(
    val uuid: String,
    val name: String,
    val isActive: Boolean,
    val lang: String?,
    val usageLimitGB: Double?
)