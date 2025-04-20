package data.api.payment

import data.dto.payment.PaymentDto
import data.dto.payment.request.PaymentRequestDto
import data.dto.payment.webhook.WebhookDto
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import java.util.Base64
import java.util.UUID
import javax.inject.Named

class RetrofitPaymentClient @Inject constructor(
    @Named("paymentRetrofit") private val apiClient: PaymentApiClient
) {

    /**
     * @param createPayment Создает платеж в YooKassa.
     * @param generateAuthHeader Заголовок авторизации (`Basic shopId:secretKey`).
     * @param paymentRequest Объект платежа.
     * @return Объект PaymentDto с деталями платежа.
     */
    suspend fun createPayment(paymentRequest: PaymentRequestDto): PaymentDto {
        val authHeader = generateAuthHeader()
        val idempotenceKey = UUID.randomUUID().toString()
        return doRequest { apiClient.createPayment(authHeader, idempotenceKey, paymentRequest) }
    }

    /**
     * @param createWebhook Запрос позволяет подписаться на уведомления о событиях
     * (например, переход платежа в статус succeeded).
     * C помощью webhook можно подписаться только на события платежей и возвратов.
     *
     */

    suspend fun createWebhook(event: String, url: String): WebhookDto {
        val authHeader = generateAuthHeader()
        val webhookDto = WebhookDto(event = event, url = url)
        return doRequest { apiClient.createWebhook(authHeader, webhookDto) }
    }

    /**
     * @param generateAuthHeader Генерация заголовка авторизации для Basic Auth
     */
    fun generateAuthHeader(): String {
        val shopId = System.getenv("SHOP_ID") ?: throw IllegalStateException("SHOP_ID отсутствует")
        val secretKey = System.getenv("PAYMENT_API_KEY_TEST") ?: throw IllegalStateException("SECRET_KEY отсутствует")
        val credentials = "$shopId:$secretKey".toByteArray()
        return "Basic ${Base64.getEncoder().encodeToString(credentials)}"
    }

    /**
     * Универсальный метод для обработки запросов
     */
    private suspend fun <T> doRequest(request: suspend () -> T): T {
        return try {
            request()
        } catch (e: HttpException) {
            throw Exception("HTTP ERROR: ${e.code()} - ${e.message}")
        } catch (e: IOException) {
            throw Exception("Network ERROR: ${e.message}")
        } catch (e: Exception) {
            throw Exception("Unexpected Error: ${e.message}")
        }
    }

}