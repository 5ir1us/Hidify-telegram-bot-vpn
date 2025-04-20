package domain.usecase.hidify

import domain.model.AllConfigUser
import domain.model.Config
import domain.repositories.ConfigRepository
import domain.usecase.ConfigUseCase
import kotlinx.coroutines.delay

class ConfigUseCaseImpl(
    private val configRepository: ConfigRepository
) : ConfigUseCase {
    override suspend fun getConfig(secretUuid: String): Config {
        return configRepository.getConfig(secretUuid)

    }

    override suspend fun tryGetConfigWithRetry(
        uuid: String,
        retries: Int,
        delayMs: Long
    ): Config? {
        repeat(retries) { attempt ->
            val config = getConfig(uuid)
            println("🔁 Попытка $attempt: config = $config")
            if (!config.full_url.isNullOrBlank()) return config
            delay(delayMs)
        }
        println("❌ Не удалось получить config после $retries попыток")
        return null
    }

    override suspend fun getAllConfig(): AllConfigUser {
        return configRepository.getAllConfigUser()
    }


}


