package data.api.payment

import data.dto.payment.PaymentDto
import data.dto.payment.request.PaymentRequestDto
import data.dto.payment.webhook.WebhookDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Интерфейс API-клиента для YooKassa.
 */
interface PaymentApiClient {

    /**
     * @param createPayment Создает платеж в YooKassa.
     * @param authHeader Заголовок авторизации (`Basic shopId:secretKey`).
     * @param paymentRequest Объект платежа.
     * @return Объект `PaymentDto` с деталями платежа.
     */
    @POST("payments")
    suspend fun createPayment(
        @Header("Authorization") authHeader: String,
        @Header("Idempotence-Key") idempotenceKey: String,
        @Body paymentRequest: PaymentRequestDto
    ): PaymentDto

    /**
     * @param authHeader Заголовок авторизации (`Basic shopId:secretKey`).
     * @param createWebhook Запрос позволяет подписаться на уведомления о событиях
     * (например, переход платежа в статус succeeded).
     * C помощью webhook можно подписаться только на события платежей и возвратов.
     * @param WebhookDto Объект
     */

    @POST("webhooks")
    suspend fun createWebhook(
        @Header("Authorization") authHeader: String,
        @Body webhookDto: WebhookDto
    ): WebhookDto
}