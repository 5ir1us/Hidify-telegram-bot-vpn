package data.api.vpn

import data.dto.vpn.request.CreateUserRequestDto
import data.dto.vpn.response.AllConfigUserDto
import data.dto.vpn.response.UserDto
import data.dto.vpn.response.DeleteUserResponse
import data.dto.vpn.response.GetInfoLinkSubscriber
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class RetrofitHidifyClient @Inject constructor(
    @Named("vpnApiClient") private val apiClient: IHidifyApiClient
) : NetworkClient {
    override suspend fun <T> doRequest(request: suspend () -> T): T {
        return try {
            request()
        } catch (e: HttpException) {
            throw Exception("HTTP ERROR: ${e.code()} - ${e.message}")
        } catch (e: IOException) {
            throw Exception("Network ERROR: ${e.message}")
        } catch (e: Exception) {
            throw Exception("Unexpected Error: ${e.message}")
        }
    }

    /**
     * Создает нового пользователя через Hiddify API.
     */
    suspend fun createUser(
        user: CreateUserRequestDto
    ): UserDto {
        val apiKey = System.getenv("HIDDIFY_API_KEY")
        val proxyPathAdmin = System.getenv("HIDDIFY_PROXY_PATCH_ADMIN")
        return doRequest { apiClient.createUser(apiKey, proxyPathAdmin, user) }
    }

    suspend fun deleteUser(
        uuid: String
    ): DeleteUserResponse {
        val apiKey = System.getenv("HIDDIFY_API_KEY")
        val proxyPathAdmin = System.getenv("HIDDIFY_PROXY_PATCH_ADMIN")
        return doRequest {
            apiClient.deleteUser(apiKey, proxyPathAdmin, uuid)
        }
    }

    suspend fun getUserInfo(
        uuid: String
    ): GetInfoLinkSubscriber {
        val apiKey = System.getenv("HIDDIFY_API_KEY")
        val proxyPathAdmin = System.getenv("HIDDIFY_PROXY_PATCH_CLIENT")
        return doRequest {
            apiClient.getUserInfo(apiKey, proxyPathAdmin, uuid)
        }
    }
    suspend fun getAllConfigUsers (): AllConfigUserDto{
        val apiKey = System.getenv("HIDDIFY_API_KEY")
        val proxyPatchAdmin = System.getenv("HIDDIFY_PROXY_PATCH_ADMIN")
        return apiClient.getAllConfigUser(apiKey,proxyPatchAdmin)
    }

}