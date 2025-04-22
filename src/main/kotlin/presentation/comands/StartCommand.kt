package presentation.comands

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.handlers.CallbackQueryHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.ReplyMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import io.ktor.server.util.url
import presentation.utils.MessageCache
import presentation.utils.autoDeleteMessage
import javax.inject.Inject

class StartCommand @Inject constructor(
    private val buyCommand: BuyCommand,
    private val statusCommand: StatusCommand,
 ) {

    fun register(dispatcher: Dispatcher) {
        dispatcher.command("start") {
            val chatId = message.chat.id

            MessageCache.get(chatId)?.let { oldMessageId ->
                bot.deleteMessage(ChatId.fromId(chatId), oldMessageId)
            }
            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = "Добро пожаловать! Выберите действие:",
                replyMarkup = getMainMenu()
            ).fold(
                { message -> MessageCache.save(chatId, message.messageId) }, // Сохраняем ID
                { error -> println("Ошибка: $error") }
            )
        }

        dispatcher.callbackQuery("buy") {
            autoDeleteMessage {
                handleBuyCommand()
            }
        }

        dispatcher.callbackQuery("ifo") {
            autoDeleteMessage {
                handleInfo()
            }
        }

        dispatcher.callbackQuery("back_to_start") {
            autoDeleteMessage {
                handleBackToStartCommand()
            }
        }

    }

      fun getMainMenu(): ReplyMarkup {
        return InlineKeyboardMarkup.create(
            listOf(
                listOf(InlineKeyboardButton.CallbackData("💳 Купить VPN", "buy")),
                listOf(InlineKeyboardButton.CallbackData("\uD83D\uDD11 Статус подписки", "status")),
                listOf(InlineKeyboardButton.Url(
                    text = "Вся необходимая информация",
                    url = System.getenv("TELEGRAM_INFO_URL")
                )),

            )
        )
    }

    private fun CallbackQueryHandlerEnvironment.handleBuyCommand() {
        val chatId = callbackQuery.message?.chat?.id ?: return
        buyCommand.executeBuyCommand(this, chatId)
    }

    private fun CallbackQueryHandlerEnvironment.handleInfo() {
        val chatId = callbackQuery.message?.chat?.id ?: return
        bot.sendMessage(ChatId.fromId(chatId), " ")
    }

    private fun CallbackQueryHandlerEnvironment.handleBackToStartCommand() {
        val chatId = callbackQuery.message?.chat?.id ?: return
        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = "Добро пожаловать! Выберите действие:",
            replyMarkup = getMainMenu() // Показываем стартовое меню
        )
    }


}