package domain.usecase.payment

import domain.model.Webhook
import domain.repositories.PaymentRepository
import domain.usecase.CreateWebhookUseCase
import javax.inject.Inject

class CreateWebhookUseCaseImpl @Inject constructor(
    private val paymentRepository: PaymentRepository
) : CreateWebhookUseCase {

    override suspend fun createWebhook(event: String, url: String): Webhook {
        val webhookDto = paymentRepository.createWebhook(event, url)
        return Webhook(
            event = webhookDto.event,
            id = webhookDto.id,
            url = webhookDto.url
        )
    }
}