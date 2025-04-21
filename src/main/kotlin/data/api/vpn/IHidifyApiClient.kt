package data.api.vpn

import data.dto.vpn.request.CreateUserRequestDto
import data.dto.vpn.response.GetInfoLinkSubscriber
import data.dto.vpn.response.UserDto
import data.dto.vpn.response.DeleteUserResponse
import data.dto.vpn.response.AllConfigUserDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Интерфейс для взаимодействия с Hiddify API через Retrofit.
 */
interface IHidifyApiClient {

    /**
     * Создает нового пользователя в Hiddify.
     *
     * @param apiKey Ключ API для авторизации.
     * @param proxyPath Административный путь (proxy_path_ADMIN).
     * @param userRequest Данные пользователя для создания.
     * @return Созданный пользователь.
     */
    @POST("{proxy_path_admin}/api/v2/admin/user/")
    suspend fun createUser(
        @Header("Hiddify-API-Key") apiKey: String,
        @Path("proxy_path_admin") proxyPath: String,
        @Body userRequest: CreateUserRequestDto
    ): UserDto

    /**
     * Удаляет пользователя в Hiddify.
     *
     * @param apiKey Ключ API для авторизации.
     * @param proxyPath Административный путь (proxy_path_ADMIN).
     * @param uuid Уникальный идентификатор пользователя.
     * @return Результат операции удаления.
     */
    @DELETE("{proxy_path_admin}/api/v2/admin/user/{uuid}/")
    suspend fun deleteUser(
        @Header("Hiddify-API-Key") apiKey: String,
        @Path("proxy_path_admin") proxyPath: String,
        @Path("uuid") uuid: String
    ): DeleteUserResponse

    /**
     * Получает информацию о подписке пользователя.
     *
     * @param apiKey Ключ API для авторизации.
     * @param proxyPath Пользовательский путь (proxy_path_USER).
     * @param uuid Уникальный идентификатор пользователя.
     * @return Информация о подписке.
     */
    @GET("{proxy_path}/{secret_uuid}/api/v2/user/short/")
    suspend fun getUserInfo(
        @Header("Hiddify-API-Key") apiKey: String,
        @Path("proxy_path") proxyPath: String,
        @Path("secret_uuid") uuid: String
    ): GetInfoLinkSubscriber

    /**
     * Получает информацию о всех созданных пользователях.
     * @param proxyPath Пользовательский путь (proxy_path_ADMIN).
     */
    @GET("/{proxy_path}/api/v2/admin/user/")
    suspend fun getAllConfigUser(
        @Header("Hiddify-API-Key") apiKey: String,
        @Path("proxy_path") proxyPath: String,
    ): List<AllConfigUserDto>
}