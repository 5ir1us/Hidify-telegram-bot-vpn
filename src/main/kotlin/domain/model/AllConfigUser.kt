package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AllConfigUser(
    val allUsers: List<User>
)
