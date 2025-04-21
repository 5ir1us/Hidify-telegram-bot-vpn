package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllConfig(
    @SerialName("users")
    val allUsers: List<AllConfigUser>,
)
