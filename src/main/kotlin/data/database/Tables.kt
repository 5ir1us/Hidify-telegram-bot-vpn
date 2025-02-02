package data.database

import org.jetbrains.exposed.sql.Table

// Таблица пользователей
object Users : Table("users") { // Указываем имя таблицы
    val id = long("id").uniqueIndex() // Telegram ID пользователя
    override val primaryKey = PrimaryKey(id) // Устанавливаем primary key
    val status = varchar("status", 50) // Статус подписки
    val subscriptionEnd = long("subscription_end").nullable() // Дата окончания подписки
}

// Таблица VPN-ключей
object VpnKeys : Table("vpn_keys") {
    val id = integer("id").autoIncrement() // Уникальный ID ключа
    override val primaryKey = PrimaryKey(id) // Устанавливаем primary key
    val key = varchar("key", 255) // VPN-ключ
    val userId = long("user_id").references(Users.id) // Telegram ID пользователя
    val createdAt = long("created_at") // Дата создания ключа
}

// Таблица платежей
object Payments : Table("payments") {
    val id = integer("id").autoIncrement() // Уникальный ID платежа
    override val primaryKey = PrimaryKey(id) // Устанавливаем primary key
    val userId = long("user_id").references(Users.id) // Telegram ID пользователя
    val amount = decimal("amount", 10, 2) // Сумма платежа
    val status = varchar("status", 50) // Статус платежа
    val createdAt = long("created_at") // Дата создания платежа
}