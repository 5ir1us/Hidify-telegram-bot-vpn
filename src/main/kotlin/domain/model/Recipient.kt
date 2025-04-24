package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipient(
    @SerialName("account_id")
    val accountId: String? = null,
    @SerialName("gateway_id")
    val gatewayId: String? = null
)
