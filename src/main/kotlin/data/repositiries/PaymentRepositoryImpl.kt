package data.repositiries

import data.api.payment.PaymentApiClient
import domain.repositories.PaymentRepository

class PaymentRepositoryImpl(
    private val paymentApiClient: PaymentApiClient
) : PaymentRepository {

}