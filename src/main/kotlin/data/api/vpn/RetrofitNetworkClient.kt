package data.api.vpn

import data.dto.request.UserDto
import io.ktor.utils.io.errors.IOException
import retrofit2.HttpException

class RetrofitNetworkClient(
    private val apiClient: IHidifyApiClient
) : NetworkClient {
    override suspend fun <T> doRequest(request: suspend () -> T): T {
       return try {
           request()
       } catch (e: HttpException){
           throw Exception("HTTP ERROR: ${e.code()} - ${e.message}")
       }catch (e: IOException){
           throw Exception("Network ERROR: ${e.message}")
       }catch (e: Exception){
           throw Exception("Unexpected Error: ${e.message}")
       }
    }
    /**
     * Создает нового пользователя через Hiddify API.
     */
    suspend fun createUser(apiKey: String, proxyPathAdmin: String, userRequest: UserDto) {
        doRequest {
            apiClient.createUser(apiKey, proxyPathAdmin, userRequest)
        }
    }

}