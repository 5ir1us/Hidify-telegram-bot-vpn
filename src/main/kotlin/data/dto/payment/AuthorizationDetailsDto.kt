package data.dto.payment

import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationDetailsDto(
    val auth_code: String,
    val rrn: String,
    val three_d_secure: ThreeDSecureDto
)