package domain.usecase

import domain.model.AllConfigUser
import domain.model.Config

interface ConfigUseCase {
 suspend fun getConfig(secretUuid: String): Config
 suspend fun tryGetConfigWithRetry(uuid: String, retries: Int = 10, delayMs: Long = 3000): Config?
 suspend fun getAllConfig(): AllConfigUser
 }