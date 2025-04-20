package domain.repositories

import domain.model.User

/**
 * @property isActive Указывает, активен ли пользователь для использования Hiddify.
 * @property lang Язык пользователя. Поддерживаемые значения: "en", "fa", "ru", "pt", "zh" или null.
 * @property name Имя пользователя. Это обязательное поле.
 * @property packageDays Количество дней в пакете пользователя. Может быть null.
 * @property telegramId Идентификатор Telegram, связанный с пользователем. Может быть null.
 * @property usageLimitGB Лимит использования в гигабайтах. Может быть null.
 * @property enable Указывает, активен ли пользователь для использования Hiddify.
 */
interface UserRepository {
    suspend fun createUser(
        enable: Boolean,
        isActive: Boolean,
        lang: String,
        name: String,
        packageDays: Int?,
        telegramId: Int,
        usageLimitGB: Double?
    ): User
    suspend fun deleteUser(uuid: String): Boolean
}