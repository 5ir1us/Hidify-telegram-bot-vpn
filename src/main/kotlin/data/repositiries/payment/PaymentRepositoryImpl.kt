package data.repositiries.payment

import data.api.payment.RetrofitPaymentClient
import data.dto.payment.AmountDto
import data.dto.payment.request.ConfirmationRequestDto
import data.dto.payment.request.PaymentRequestDto
import data.utils.UserMapper
import domain.model.Payment
import domain.model.Webhook
import domain.repositories.PaymentRepository
import javax.inject.Inject

/**
 * Реализация репозийтория для работы с YooKassa.
 */
class PaymentRepositoryImpl @Inject constructor(
    private val paymentClient: RetrofitPaymentClient
) : PaymentRepository {
    override suspend fun createPayment(
        amountValue: String,
        amountCurrency: String,
        automaticReceptionOfTheReceivedPayment: Boolean,
        type: String,
        returnUrl: String,
        description: String
    ): Payment {

        val amount = AmountDto(
            currency = amountCurrency,
            value = amountValue
        )
        val confirmationDto = ConfirmationRequestDto(
            type = type,
            return_url = returnUrl
        )

        val paymentRequestDto = PaymentRequestDto(
            amount = amount,
            capture = automaticReceptionOfTheReceivedPayment,
            confirmation = confirmationDto,
            description = description
        )

        val paymentDto = paymentClient.createPayment(paymentRequestDto)
        return UserMapper.fromPayment(paymentDto)
    }

    override suspend fun createWebhook(event: String, url: String): Webhook {
        val requestWebhook = paymentClient.createWebhook(event, url)
        val result = UserMapper.fromWebhook(requestWebhook)
        return result
    }


}