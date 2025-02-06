package data.dto.payment

import kotlinx.serialization.Serializable

@Serializable
data class ThreeDSecureDto(
    val applied: Boolean
)