package data.dto.payment

import kotlinx.serialization.Serializable

@Serializable
data class PayDto(
    val amount: AmountDto,
    val authorization_details: AuthorizationDetailsDto,
    val created_at: String,
    val description: String,
    val expires_at: String,
    val id: String,
    val income_amount: IncomeAmountDto,
    val metadata: MetadataDto,
    val paid: Boolean,
    val payment_method: PaymentMethodDto,
    val recipient: RecipientDto,
    val refundable: Boolean,
    val status: String,
    val test: Boolean
)