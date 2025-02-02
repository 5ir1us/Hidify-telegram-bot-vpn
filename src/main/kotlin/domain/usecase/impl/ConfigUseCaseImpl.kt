package domain.usecase.impl

import domain.model.Config
import domain.repositories.ConfigRepository
import domain.repositories.UserRepository
import domain.usecase.ConfigUseCase

class ConfigUseCaseImpl (
    private val configRepository: ConfigRepository
) : ConfigUseCase {
    override suspend fun getConfig(secretUuid: String): Config {
        return configRepository.getConfig(secretUuid)

    }

}