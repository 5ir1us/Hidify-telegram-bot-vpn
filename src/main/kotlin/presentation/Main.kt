package presentation

import data.database.AppDatabase
import di.DaggerAppComponent


fun main() {
    // Подключаем базу данных
    AppDatabase.connect()

    // Получаем зависимости через Dagger
    val appComponent = DaggerAppComponent.create()
    val bot = appComponent.getTelegramBot()

    // Запускаем Telegram-бота
    bot.start()
}