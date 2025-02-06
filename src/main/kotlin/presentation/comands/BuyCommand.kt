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

class BuyCommand @Inject constructor() {


    fun register(dispatcher: Dispatcher) {
        dispatcher.command("buy") {
            val chatId = message.chat.id
            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = "Выберите период подписки:",
                replyMarkup = getBuyMenu()
            )
        }
        dispatcher.callbackQuery("buy_1m") { handleBuyCallback("1 месяц", 300) }
        dispatcher.callbackQuery("buy_3m") { handleBuyCallback("3 месяца", 800) }
    }

    fun executeBuyCommand(callbackQueryHandler: CallbackQueryHandlerEnvironment, chatId: Long) {
        callbackQueryHandler.bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = "Выберите период подписки:",
            replyMarkup = getBuyMenu()
        )
    }

    private fun getBuyMenu(): ReplyMarkup {
        return InlineKeyboardMarkup.create(
            listOf(
                listOf(InlineKeyboardButton.CallbackData("1 месяц - 300 ₽", "buy_1m")),
                listOf(InlineKeyboardButton.CallbackData("3 месяца - 800 ₽", "buy_3m"))
            )
        )
    }

    private fun CallbackQueryHandlerEnvironment.handleBuyCallback(period: String, price: Int) {
        val chatId = callbackQuery.message?.chat?.id ?: return
        bot.sendMessage(ChatId.fromId(chatId), "✅ Вы выбрали подписку на $period за $price ₽.")
        // userUseCase.createUser(chatId, period)  // Здесь добавьте логику покупки
    }
}