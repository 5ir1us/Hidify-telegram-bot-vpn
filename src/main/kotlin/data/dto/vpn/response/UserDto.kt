package data.dto.vpn.response

import kotlinx.serialization.Serializable

/**
 * Представляет пользователя в системе Hiddify.
 *
 * @property addedByUuid UUID администратора, добавившего этого пользователя. Может быть null.
 * @property comment Необязательный комментарий о пользователе. Может быть null.
 * @property currentUsageGB Текущий объем использования в гигабайтах. Может быть null.
 * @property ed25519PrivateKey Приватный ключ Ed25519 пользователя. Может быть null.
 * @property ed25519PublicKey Публичный ключ Ed25519 пользователя. Может быть null.
 * @property enable Указывает, активен ли пользователь для использования Hiddify.
 * @property id Идентификатор пользователя. Используется только для презентации и не должен использоваться в логике приложения. Может быть null.
 * @property isActive Указывает, активен ли пользователь для использования Hiddify.
 * @property lang Язык пользователя. Поддерживаемые значения: "en", "fa", "ru", "pt", "zh" или null.
 * @property lastOnline Дата и время последнего онлайн-активности пользователя в формате, удобном для JSON. Может быть null.
 * @property lastResetTime Дата и время последнего сброса данных использования пользователем в формате, удобном для JSON. Может быть null.
 * @property mode  сброс пакета Возможные значения: "no_reset", "monthly", "weekly", "daily" или null.
 * @property name Имя пользователя. Это обязательное поле.
 * @property packageDays Количество дней в пакете пользователя. Может быть null.
 * @property startDate Дата начала действия пользовательского пакета в формате, удобном для JSON. Может быть null.
 * @property telegramId Идентификатор Telegram, связанный с пользователем. Может быть null.
 * @property usageLimitGB Лимит использования в гигабайтах. Может быть null.
 * @property uuid Уникальный идентификатор пользователя. Может быть null.
 * @property wgPk Публичный ключ WireGuard. Может быть null.
 * @property wgPsk Приватный ключ WireGuard. Может быть null.
 * @property wgPub Публичный ключ WireGuard. Может быть null.
 */
@Serializable
data class UserDto(
    val addedByUuid: String?,
    val comment: String?,
    val currentUsageGB: Double?,
    val ed25519PrivateKey: String?,
    val ed25519PublicKey: String?,
    val enable: Boolean, //активность впн
    val id: Int?,
    val isActive: Boolean, //актив
    val lang: String?, //язык
    val lastOnline: String?,
    val lastResetTime: String?,
    val mode: String?,
    val name: String, //имя пользователя ***Обязательный параметр
    val packageDays: Int?, // дни работы
    val startDate: String?,
    val telegramId: Int?, //телеграм йд
    val usageLimitGB: Double?,
    val uuid: String?,//
    val wgPk: String?,
    val wgPsk: String?,
    val wgPub: String?
)