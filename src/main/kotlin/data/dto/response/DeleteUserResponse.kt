package data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class DeleteUserResponse(
    val msg: String,
    val status: Int
)