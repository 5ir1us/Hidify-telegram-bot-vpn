package data.dto.vpn.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllConfigDto(
    @SerialName("all_configs")
    val allUser: List<AllConfigUserDto>?,

)
