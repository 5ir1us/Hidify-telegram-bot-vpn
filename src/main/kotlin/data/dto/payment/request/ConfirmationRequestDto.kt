package data.dto.payment.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationRequestDto(
    val type: String,
    @SerialName("return_url")
    val return_url: String
)
