package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param type Значение — redirect.
 * Код сценария подтверждения.
 *
 * @param  confirmation_url URL, на который необходимо перенаправить пользователя для подтверждения оплаты.
 *
 * @param  return_url URL, на который вернется пользователь после
 * подтверждения или отмены платежа на веб-странице. Не более 2048 символов.
 */
@Serializable
data class Confirmation(
    val type: String,
    @SerialName("return_url")
    val returnUrl: String,
    val confirmation_url: String?

)
