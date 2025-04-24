package data.dto.payment

import kotlinx.serialization.Serializable

/**
 * @param value Сумма в выбранной валюте. Всегда дробное значение. Разделитель
 * дробной части — точка, разделитель тысяч отсутствует. Количество знаков
 * после точки зависит от выбранной валюты. Пример: 1000.00.
 *
 * @param currency Трехбуквенный код валюты в формате ISO-4217. Пример: RUB.
 * Должен соответствовать валюте субаккаунта (recipient.gateway_id),
 * если вы разделяете потоки платежей,
 * и валюте аккаунта (shopId в личном кабинете), если не разделяете.
 */
@Serializable
data class AmountDto(
    val currency: String,
    val value: String
)