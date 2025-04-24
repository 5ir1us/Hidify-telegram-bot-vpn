package data.dto.vpn.response

import kotlinx.serialization.Serializable

@Serializable
data class DeleteUserResponse(
    val msg: String,
    val status: Int
)