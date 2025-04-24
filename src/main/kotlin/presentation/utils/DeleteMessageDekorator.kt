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

    messageId?.let {
        bot.deleteMessage(ChatId.fromId(chatId), it)
    }

    MessageCache.get(chatId)?.let { oldMessageId ->
        bot.deleteMessage(ChatId.fromId(chatId), oldMessageId)
        MessageCache.clear(chatId)
    }

    CoroutineScope(Dispatchers.IO).launch {
        block()
    }
}