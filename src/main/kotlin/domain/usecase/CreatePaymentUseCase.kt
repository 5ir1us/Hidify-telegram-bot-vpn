package domain.usecase

import domain.model.Payment



interface CreatePaymentUseCase    {

    suspend fun paymentNew(
        amount: String,
        description: String,
    ): Payment

}
