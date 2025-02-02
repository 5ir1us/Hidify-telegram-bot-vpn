package data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val addedByUuid: String?,//UUID администратора, добавившего этого пользователя
    val comment: String?,//Необязательный комментарий о пользователе
    val currentUsageGB: Double?,
    val ed25519PrivateKey: String?,
    val ed25519PublicKey: String?,
    val enable: Boolean,
    val id: Int?,//никогда не используйте его, только для лучшей презентации
    val isActive: Boolean,//Активен ли пользователь для использования hiddify
    val lang: String?,//Язык пользователя en fa ru pt zh null
    val lastOnline: String?, //Последний раз, когда пользователь был онлайн, преобразованный в формат, удобный для JSON
    val lastResetTime: String?,//В последний раз использование данных пользователем было сброшено в удобном для JSON формате
    val mode: String?,//no_reset monthly weekly daily null Режим учетной записи пользователя, который определяет уровень или тип доступа
    val name: String,//  Имя пользователя ТРЕБУЕМЫЙ
    val packageDays: Int?, //Количество дней в пакете пользователя
    val startDate: String?,//Дата начала действия пользовательского пакета в формате, удобном для JSON
    val telegramId: Int?, //Идентификатор Telegram, связанный с пользователем
    val usageLimitGB: Double?,
    val uuid: String?,
    val wgPk: String?,
    val wgPsk: String?,
    val wgPub: String?
)