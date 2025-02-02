package data.api.vpn

import data.dto.response.GetInfoLinkSubscriber
import data.dto.request.UserDto
import data.dto.response.DeleteUserResponse
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
     * @param proxyPath Административный путь (proxy_path_admin).
     * @param userRequest Данные пользователя для создания.
     * @return Созданный пользователь.
     */
    @POST("{proxy_path_admin}/api/v2/admin/user/")
    suspend fun createUser(
        @Header("Hiddify-API-Key") apiKey: String,
        @Path("proxy_path_admin") proxyPath: String,
        @Body userRequest: UserDto
    )

    /**
     * Удаляет пользователя в Hiddify.
     *
     * @param apiKey Ключ API для авторизации.
     * @param proxyPath Административный путь (proxy_path_admin).
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
     * @param proxyPath Пользовательский путь (proxy_path_user).
     * @param uuid Уникальный идентификатор пользователя.
     * @return Информация о подписке.
     */
    @GET("{proxy_path_user}/{secret_uuid}/api/v2/user/me/")
    suspend fun getUserInfo(
        @Header("Hiddify-API-Key") apiKey: String,
        @Path("proxy_path_user") proxyPath: String,
        @Path("secret_uuid") uuid: String
    ): GetInfoLinkSubscriber
}