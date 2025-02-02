package presentation

import data.database.AppDatabase

fun main() {
    // Подключаем базу данных
    AppDatabase.connect()

    // Создаём и запускаем Telegram-бота
    val bot = TelegramBotRoot()
    bot.start()
}