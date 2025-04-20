package presentation.comands

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.handlers.CallbackQueryHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.ReplyMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import domain.usecase.UserUseCase
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class StartCommand @Inject constructor(
    private val buyCommand: BuyCommand,
    private val statusCommand: StatusCommand,
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
        dispatcher.callbackQuery("ifo") { this.handleCancelInfo() }
        dispatcher.callbackQuery("back_to_start") { this.handleBackToStartCommand() }

    }

      fun getMainMenu(): ReplyMarkup {
        return InlineKeyboardMarkup.create(
            listOf(
                listOf(InlineKeyboardButton.CallbackData("💳 Купить VPN", "buy")),
                listOf(InlineKeyboardButton.CallbackData("📊 Статус подписки", "status")),
                listOf(InlineKeyboardButton.CallbackData("🧪  Информация", "ifo")),

            )
        )
    }

    private fun CallbackQueryHandlerEnvironment.handleBuyCommand() {
        val chatId = callbackQuery.message?.chat?.id ?: return
        buyCommand.executeBuyCommand(this, chatId)
    }

    private fun CallbackQueryHandlerEnvironment.handleStatusCommand() {
        val chatId = callbackQuery.message?.chat?.id ?: return
        println("✅ Запрос статуса подписки от пользователя: $chatId")

    }

    private fun CallbackQueryHandlerEnvironment.handleCancelInfo() {
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