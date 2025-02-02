package presentation

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.logging.LogLevel

class TelegramBotRoot {

    private val bot = bot {
        token = System.getenv("TELEGRAM_BOT_TOKEN") ?: throw IllegalStateException("TELEGRAM_BOT_TOKEN отсутствует")
        logLevel = LogLevel.Network.Body

        dispatch {
            command("start") {
                val chatId = message.chat.id
                val result = bot.sendMessage(ChatId.fromId(chatId), "Добро пожаловать! Используйте команду /buy для покупки VPN-ключей.")
                result.fold(
                    {
                        // Сообщение успешно отправлено
                    },
                    {
                        // Произошла ошибка при отправке сообщения

                    }
                )
            }
        }
    }

    fun start() {
        println("Запускаем Telegram-бота...")
        bot.startPolling()
    }
}