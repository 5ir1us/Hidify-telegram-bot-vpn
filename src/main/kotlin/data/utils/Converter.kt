package data.utils

import data.dto.request.UserDto
  import domain.model.User


object UserMapper {
    fun toDomain(dto: UserDto): User {
        return User(
            uuid = dto.uuid.orEmpty(),
            name = dto.name,
            isActive = dto.isActive,
            lang = dto.lang,
            usageLimitGB = dto.usageLimitGB
        )
    }

    fun fromDomain(user: User): UserDto {
        return UserDto(
            uuid = user.uuid,
            name = user.name,
            isActive = user.isActive,
            lang = user.lang,
            usageLimitGB = user.usageLimitGB,
            addedByUuid = null,
            comment = null,
            currentUsageGB = null,
            ed25519PrivateKey = null,
            ed25519PublicKey = null,
            enable = true,
            id = null,
            lastOnline = null,
            lastResetTime = null,
            mode = "no_reset",
            packageDays = null,
            startDate = null,
            telegramId = null,
            wgPk = null,
            wgPsk = null,
            wgPub = null
        )
    }

}