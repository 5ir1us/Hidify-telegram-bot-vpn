package domain.repositories

 import domain.model.Payment
import domain.model.Webhook

interface PaymentRepository {

    suspend fun createPayment(
        amountValue: String,
        amountCurrency: String,
        automaticReceptionOfTheReceivedPayment: Boolean,
        type: String,
        returnUrl: String,
        description: String
    ): Payment


    suspend fun createWebhook(event: String, url: String): Webhook
}