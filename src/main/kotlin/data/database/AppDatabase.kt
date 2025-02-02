package data.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


object AppDatabase {
    fun connect() {
        val dbUrl = System.getenv("DATABASE_URL") ?: throw IllegalStateException("DATABASE_URL is missing")
        val dbUser = System.getenv("DATABASE_USER") ?: throw IllegalStateException("DATABASE_USER is missing")
        val dbPassword =System.getenv("DATABASE_PASSWORD") ?: throw IllegalStateException("DATABASE_PASSWORD is missing")
        try {
        Database.connect(
            url = dbUrl,
            driver = "org.postgresql.Driver",
            user = dbUser,
            password = dbPassword
        )
            println("База данных подключена: $dbUrl")
        } catch (e: Exception) {
            println("Ошибка подключения к базе данных: ${e.message}")
            throw e
        }

        // Автоматическое создание таблиц, если их нет
        transaction {
            SchemaUtils.create(Users, VpnKeys, Payments)
        }

        println("База данных подключена: $dbUrl")
    }
}
