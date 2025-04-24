package data.dto.payment.request

import data.dto.payment.AmountDto
import data.dto.payment.ConfirmationResponseDto
import kotlinx.serialization.Serializable

/***
 *@param amount
 *  Сумма платежа /Сумма в выбранной валюте /Трехбуквенный код валюты в формате ISO-4217. Пример: RUB
 *@param capture
 *  * Автоматический прием поступившего платежа. Возможные значения:
 *  * true — оплата списывается сразу (платеж в одну стадию);
 *  * false — оплата холдируется и списывается по вашему запросу (платеж в две стадии).
 *  * По умолчанию false.
 *@param confirmation
 *  Данные, необходимые для инициирования выбранного сценария подтверждения платежа пользователем. Подробнее
 *@param description
 * Описание транзакции (не более 128 символов), которое вы увидите в личном кабинете ЮKassa,
 *  а пользователь — при оплате. Например: «Оплата заказа № 72 для user@yoomoney.ru».
 */
@Serializable
data class PaymentRequestDto(
    val amount: AmountDto,
    val capture: Boolean,
    val confirmation: ConfirmationRequestDto,
    val description: String
)
