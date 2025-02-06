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
import javax.inject.Inject


class StatusCommand @Inject constructor(
    private val configUseCase: ConfigUseCase
) {
    private val scope = CoroutineScope(Dispatchers.IO) // ✅ Создаем корутину

    fun register(dispatcher: Dispatcher) {
        dispatcher.command("status") { scope.launch { sendSubscriptionStatus(bot, message.chat.id) } }

        dispatcher.callbackQuery("status") { scope.launch { sendSubscriptionStatus(bot, callbackQuery.message?.chat?.id ?: return@launch) } }
    }

    // ✅ Получаем ключи подписчика и отправляем их
    private suspend fun sendSubscriptionStatus(bot: Bot, chatId: Long) {
        val userConfig = configUseCase.getConfig(chatId.toString()) // ⚡ Получаем конфиг подписчика

        val responseText = if (userConfig == null) {
            "❌ У вас нет активных подписок."
        } else {
            "📊 Ваши купленные VPN-ключи:\n\n🔑 ${userConfig.link}"
        }

        bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = responseText
        )
    }

}