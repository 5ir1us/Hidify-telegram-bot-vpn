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


class InfoCommand @Inject constructor() {


    fun register(dispatcher: Dispatcher) {
        dispatcher.command("cancel") {
            val chatId = message.chat.id
            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = "❗ Вы уверены, что хотите отменить подписку?",
                replyMarkup = getCancelMenu()
            )
        }
        dispatcher.callbackQuery("cancel_confirm") { handleCancelCallback() }

    }
    private fun getCancelMenu(): ReplyMarkup {
        return InlineKeyboardMarkup.create(
            listOf(
                listOf(InlineKeyboardButton.CallbackData("✅ Да, отменить", "cancel_confirm")),
                listOf(InlineKeyboardButton.CallbackData("❌ Нет, оставить", "cancel_decline"))
            )
        )
    }

    private fun CallbackQueryHandlerEnvironment.handleCancelCallback() {
        val chatId = callbackQuery.message?.chat?.id ?: return
        bot.sendMessage(ChatId.fromId(chatId), "🚫 Подписка отменена.")

    }
}