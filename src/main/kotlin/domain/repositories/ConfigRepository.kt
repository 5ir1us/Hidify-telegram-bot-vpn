package domain.repositories

 import domain.model.Config

interface ConfigRepository {
    suspend fun getConfig(secretUuid: String): Config
}