package data.dto.payment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipientDto (
    @SerialName("account_id")
    val accountId: String? = null,
    @SerialName("gateway_id")
    val gatewayId: String? = null
)