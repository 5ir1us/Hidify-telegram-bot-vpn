package presentation.comands
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.handlers.CallbackQueryHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.ReplyMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import javax.inject.Inject

class StartCommand @Inject constructor(
    private val buyCommand: BuyCommand,
    private val statusCommand: StatusCommand
) {

    fun register(dispatcher: Dispatcher) {
        dispatcher.command("start") {
            val chatId = message.chat.id
            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = "Добро пожаловать! Выберите действие:",
                replyMarkup = getMainMenu()
            )
        }
        dispatcher.callbackQuery("buy") { this.handleBuyCommand() }
        dispatcher.callbackQuery("status") { this.handleStatusCommand() }
        dispatcher.callbackQuery("cancel") { this.handleCancelCommand() }
    }
    private fun getMainMenu(): ReplyMarkup {
        return InlineKeyboardMarkup.create(
            listOf(
                listOf(InlineKeyboardButton.CallbackData("💳 Купить VPN", "buy")),
                listOf(InlineKeyboardButton.CallbackData("📊 Статус подписки", "status")),
                listOf(InlineKeyboardButton.CallbackData("🚫 Отменить подписку", "cancel"))
            )
        )
    }
    private fun CallbackQueryHandlerEnvironment.handleBuyCommand() {
        val chatId = callbackQuery.message?.chat?.id ?: return
        buyCommand.executeBuyCommand(this, chatId) // Передаем управление в BuyCommand
    }

    private fun CallbackQueryHandlerEnvironment.handleStatusCommand() {
        val chatId = callbackQuery.message?.chat?.id ?: return
        println("✅ Запрос статуса подписки от пользователя: $chatId") // ✅ Логирование

    }


    private fun CallbackQueryHandlerEnvironment.handleCancelCommand() {
        val chatId = callbackQuery.message?.chat?.id ?: return
        bot.sendMessage(ChatId.fromId(chatId), " ")
    }
}