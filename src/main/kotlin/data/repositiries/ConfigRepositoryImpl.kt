package data.repositiries

import data.api.vpn.RetrofitHidifyClient
import data.dto.vpn.response.GetInfoLinkSubscriber
import data.utils.ConfigMapper
import data.utils.UserMapper.toConfigAllUser
import domain.model.Config
import domain.model.AllConfig
import domain.repositories.ConfigRepository

class ConfigRepositoryImpl(
    private val apiClient: RetrofitHidifyClient
) : ConfigRepository {

    override suspend fun getConfig(secretUuid: String): Config {
        val dto: GetInfoLinkSubscriber = apiClient.getUserInfo(secretUuid)
        return ConfigMapper.toDomain(dto)
    }

    override suspend fun getAllConfigUser(): AllConfig {
        val dtoList = apiClient.getAllConfigUsers()
        val domainList = dtoList.map { it.toConfigAllUser() }
        return AllConfig(
            allUsers = domainList
        )
    }


}