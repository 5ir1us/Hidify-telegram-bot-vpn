package presentation.comands

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.handlers.CallbackQueryHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import domain.usecase.ConfigUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import presentation.utils.MessageCache
import presentation.utils.autoDeleteMessage

import javax.inject.Inject


class StatusCommand @Inject constructor(
    private val configUseCase: ConfigUseCase
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun register(dispatcher: Dispatcher) {
        dispatcher.callbackQuery("status") {
            autoDeleteMessage {
                handleStatusCommand()
            }
        }
    }

    private fun CallbackQueryHandlerEnvironment.handleStatusCommand() {
        val chatId = callbackQuery.message?.chat?.id ?: return

        scope.launch {
            try {
                sendSubscriptionStatus(bot, chatId)
            } catch (e: Exception) {
                println("[ERROR] Ошибка при обработке статуса: ${e.stackTraceToString()}")
                bot.sendMessage(
                    chatId = ChatId.fromId(chatId),
                    text = "⚠ Произошла ошибка при получении статуса"
                ).fold(
                    { message -> MessageCache.save(chatId, message.messageId) },
                    { error -> println("Ошибка: $error") }
                )
            }
        }
    }

    private suspend fun sendSubscriptionStatus(bot: Bot, chatId: Long) {

        val allConfigs = configUseCase.getAllConfig().also {
        }
        val userConfig = allConfigs.allUsers?.filter { user ->
            user.telegram_id == chatId
        } ?: emptyList()

        if (userConfig.isEmpty()) {
            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = " ❌ У тебя нет ключей, Купи ключ \uD83D\uDD11"
            ).fold(
                { message -> MessageCache.save(chatId, message.messageId) },
                { error -> println("Ошибка: $error") }
            )
            return
        }
        val url = System.getenv("HIDDIFY_API_URL")
        val patch = System.getenv("HIDDIFY_PROXY_PATCH_CLIENT")
        val messageText = "✅ Ваши ключи (нажмите, чтобы перейти и скопировать):"

        val buttonss = userConfig.mapIndexed { index, user ->
            val fullUrl = "$url/$patch/${user.uuid}/#${user.name}"
            listOf(
                InlineKeyboardButton.Url(
                    text = "Конфиг \uD83D\uDD11 ${index + 1}",
                    url = fullUrl
                )
            )
        } + listOf(
            listOf(
                InlineKeyboardButton.CallbackData(
                    text = "🔙 Назад",
                    callbackData = "back_to_start"
                )
            )
        )

        val keyboard = InlineKeyboardMarkup.create(buttonss)
        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = messageText,
            replyMarkup = keyboard
        ).fold(
            { message -> MessageCache.save(chatId, message.messageId) },
            { error -> println("Ошибка: $error") }
        )

    }


}