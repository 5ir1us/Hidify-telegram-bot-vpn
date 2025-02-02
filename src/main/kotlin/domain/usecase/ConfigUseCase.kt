package domain.usecase

import domain.model.Config

interface ConfigUseCase {
 suspend fun getConfig(secretUuid: String): Config
 }