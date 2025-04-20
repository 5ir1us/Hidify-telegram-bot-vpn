package data.dto.payment

import data.dto.payment.RecipientDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/***
 *@param id Идентификатор платежа Пример:1da5c87d-0984-50e8-a7f3-8de646dd9ec9
 *@param status Статус платежа. Возможные значения: pending, waiting_for_capture, succeeded и canceled
 *@param amount Сумма платежа /Сумма в выбранной валюте /Трехбуквенный код валюты в формате ISO-4217. Пример: RUB
 *@param recipient Получатель платежа. /Идентификатор магазина в ЮKassa.
 *@param createdAt Время создания заказа. Указывается по UTC и передается в формате ISO 8601. Пример: 2017-11-03T11:52:31.827Z
 *@param test Признак тестовой операции
 *@param paid Признак оплаты заказа.
 *@param refundable Возможность провести возврат по API.
 *@param confirmation Данные, необходимые для инициирования выбранного сценария подтверждения платежа пользователем.
 */
@Serializable
data class PaymentDto(
    val id: String? = null,
    val status: String,
    val amount: AmountDto,
    val recipient: RecipientDto?= null,
    val createdAt: String? = null,
    val test: Boolean? = null,
    val paid: Boolean? = null,
    val refundable: Boolean? = null,
    @SerialName("confirmation")
    val confirmation: ConfirmationResponseDto? = null
)