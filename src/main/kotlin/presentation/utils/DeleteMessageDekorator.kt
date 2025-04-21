package presentation.utils

import com.github.kotlintelegrambot.dispatcher.handlers.CallbackQueryHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun CallbackQueryHandlerEnvironment.autoDeleteMessage(
    block: suspend CallbackQueryHandlerEnvironment.() -> Unit
) {
    val chatId = callbackQuery.message?.chat?.id ?: return
    val messageId = callbackQuery.message?.messageId

    // Удаляем сообщение с командой (например, "/status")
    messageId?.let {
        bot.deleteMessage(ChatId.fromId(chatId), it)
    }

    // Удаляем предыдущий ответ бота
    MessageCache.get(chatId)?.let { oldMessageId ->
        bot.deleteMessage(ChatId.fromId(chatId), oldMessageId)
        MessageCache.clear(chatId)
    }

    // Запускаем оригинальный обработчик
    CoroutineScope(Dispatchers.IO).launch {
        block()
    }
}