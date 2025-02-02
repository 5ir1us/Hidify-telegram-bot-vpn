package data.repositiries

import data.api.vpn.IHidifyApiClient
import data.dto.response.GetInfoLinkSubscriber
import data.utils.ConfigMapper
import domain.model.Config
import domain.repositories.ConfigRepository

class ConfigRepositoryImpl(
    private val apiClient: IHidifyApiClient
) : ConfigRepository {
    override suspend fun getConfig(secretUuid: String): Config {
        val apiKey = System.getenv("HIDDIFY_API_KEY") ?: throw IllegalStateException("HIDDIFY_API_KEY отсутствует")
        val proxyPath = System.getenv("HIDDIFY_PROXY_PATCH_CLIENT")
            ?: throw IllegalStateException("HIDDIFY_PROXY_PATCH_CLIENT отсутствует")

        val dto: GetInfoLinkSubscriber = apiClient.getUserInfo(apiKey, proxyPath, secretUuid)
        return ConfigMapper.toDomain(dto)
    }
}