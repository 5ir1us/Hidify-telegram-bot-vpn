package data.repositiries

import data.api.vpn.RetrofitHidifyClient
import data.dto.vpn.request.CreateUserRequestDto
import data.dto.vpn.response.UserDto
import data.dto.vpn.response.DeleteUserResponse
import data.utils.UserMapper
import domain.model.User
import domain.repositories.UserRepository

class UserRepositoryImpl(
    private val apiClient: RetrofitHidifyClient
) : UserRepository {


    override suspend fun createUser(
        enable: Boolean,
        isActive: Boolean,
        lang: String,
        name: String,
        packageDays: Int?,
        telegramId: Int,
        usageLimitGB: Double?
    ): User {
        val userDto = CreateUserRequestDto(
            enable = enable,
            isActive = isActive,
            lang = lang,
            name = name,
            packageDays = packageDays,
            telegramId = telegramId,
            usageLimitGb = usageLimitGB
        )

        val userRequest = apiClient.createUser(userDto)
        return UserMapper.toUser(userRequest)
    }

    override suspend fun deleteUser(uuid: String): Boolean {
        val response: DeleteUserResponse = apiClient.deleteUser(uuid)
        return response.status == 200
    }

}

