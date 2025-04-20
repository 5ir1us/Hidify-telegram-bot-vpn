package domain.repositories

 import domain.model.Config
 import domain.model.AllConfigUser

interface ConfigRepository {
    suspend fun getConfig(secretUuid: String): Config

    suspend fun getAllConfigUser(): AllConfigUser
}