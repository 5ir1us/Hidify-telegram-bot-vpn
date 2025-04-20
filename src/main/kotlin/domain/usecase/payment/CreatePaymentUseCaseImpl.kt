package domain.usecase.payment

import domain.Constants.CAPTURE
import domain.Constants.CONFIRMATION_TYPE
import domain.Constants.CURRENCY
import domain.model.Payment
import domain.repositories.PaymentRepository
import domain.usecase.CreatePaymentUseCase
import javax.inject.Inject

class CreatePaymentUseCaseImpl @Inject constructor(
    private val paymentRepository: PaymentRepository
) : CreatePaymentUseCase {

    override suspend fun paymentNew(
        amount: String,
        description: String,

        ): Payment {
        val returnUrl = System.getenv("TELEGRAM_URL")
        return paymentRepository.createPayment(
            amountValue = amount,
            amountCurrency = CURRENCY,
            automaticReceptionOfTheReceivedPayment = CAPTURE,
            type = CONFIRMATION_TYPE,
            returnUrl = returnUrl,
            description = description
        )
    }

}