package data.repositiries

import data.api.vpn.IHidifyApiClient
import data.dto.response.DeleteUserResponse
import data.utils.UserMapper
import domain.model.User
import domain.repositories.UserRepository

class UserRepositoryImpl(
    private val apiClient: IHidifyApiClient
) : UserRepository {


    override suspend fun createUser(user: User) {
        val userDto = UserMapper.fromDomain(user)
        val apiKey = System.getenv("HIDDIFY_API_KEY") ?: throw IllegalStateException("HIDDIFY_API_KEY отсутствует")
        val proxyPath = System.getenv("HIDDIFY_PROXY_PATCH_ADMIN") ?: throw IllegalStateException("HIDDIFY_PROXY_PATCH_ADMIN отсутствует")

        apiClient.createUser(apiKey, proxyPath, userDto)
    }

    override suspend fun deleteUser(uuid: String): Boolean {
        val apiKey = System.getenv("HIDDIFY_API_KEY") ?: throw IllegalStateException("HIDDIFY_API_KEY отсутствует")
        val proxyPath = System.getenv("HIDDIFY_PROXY_PATCH_ADMIN") ?: throw IllegalStateException("HIDDIFY_PROXY_PATCH_ADMIN отсутствует")

        val response: DeleteUserResponse = apiClient.deleteUser(apiKey, proxyPath, uuid)
        return response.status == 200
    }

}