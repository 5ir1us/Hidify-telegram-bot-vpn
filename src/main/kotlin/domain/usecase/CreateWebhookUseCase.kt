package domain.usecase

import domain.model.Payment
import domain.model.Webhook

interface CreateWebhookUseCase {
    suspend fun createWebhook(event: String, url: String): Webhook
}