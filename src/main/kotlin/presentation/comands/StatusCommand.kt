package presentation.comands

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId
import domain.usecase.ConfigUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class StatusCommand @Inject constructor(
    private val configUseCase: ConfigUseCase
) {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun register(dispatcher: Dispatcher) {
        dispatcher.command("status") {
            runBlocking(Dispatchers.IO) {
                val chatId = message.chat.id
                scope.launch {
                    sendSubscriptionStatus(bot, chatId)
                }
            }
        }

        dispatcher.callbackQuery("status") {
            runBlocking(Dispatchers.IO) {
            val chatId = callbackQuery.message?.chat?.id ?: return@runBlocking
            scope.launch {
                sendSubscriptionStatus(bot, chatId)
            }
        }
        }
    }


    // ✅ Получаем ключи подписчика и отправляем их
    private suspend fun sendSubscriptionStatus(bot: Bot, chatId: Long) {
        val allConfigs = configUseCase.getAllConfig()
        val userConfig = allConfigs.allUsers.filter { it.telegramId?.toLong() == chatId }

        if (userConfig.isEmpty()) {
            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = " ❌ У тебя нет ключей, ты еще не с нами ?\""
            )
            return
        }
        val responseText = buildString {
            append("✅ Ваши UUID:\n\n")
            userConfig.forEachIndexed { index, user ->
                append("${index + 1}. ${user.uuid}\n")

            }
        }

        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = responseText
        )
    }

}