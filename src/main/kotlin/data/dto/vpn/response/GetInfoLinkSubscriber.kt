package data.dto.vpn.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetInfoLinkSubscriber(
    @SerialName("expire_in")
    val expire_in: Int?,

    @SerialName("full_url")
    val full_url: String?,

    @SerialName("short")
    val short: String?,


)