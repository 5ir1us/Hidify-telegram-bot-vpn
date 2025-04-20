package data.dto.vpn.response

import kotlinx.serialization.Serializable

@Serializable
data class AllConfigUserDto(
      val allUsers: List<UserDto>
)
