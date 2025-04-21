package presentation.utils

import com.github.kotlintelegrambot.dispatcher.handlers.CallbackQueryHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import presentation.utils.MessageCache
import retrofit2.Response
 import com.github.kotlintelegrambot.entities.Message

/**
 * 1) Удаляет старое сообщение из кэша (если было).
 * 2) Отправляет фото по URL с подписью и кнопками.
 * 3) Сохраняет ID нового сообщения в MessageCache.
 */
fun CallbackQueryHandlerEnvironment.sendPhotoWithKeyboardReplacingOld(
    chatId: Long,
    photoUrl: String,
    caption: String,
    replyMarkup: InlineKeyboardMarkup
) {
    val chatIdObj = ChatId.fromId(chatId)

    // 1) удаляем старое сообщение
    MessageCache.get(chatId)?.let { oldMessageId ->
        bot.deleteMessage(chatId = chatIdObj, messageId = oldMessageId)
        MessageCache.clear(chatId)
    }

    // 2) отправляем новое фото
    val (response: Response<TelegramResponse<Message>>?, exception: Exception?) =
        bot.sendPhoto(
            chatId = chatIdObj,
            photo = photoUrl,
            caption = caption,
            replyMarkup = replyMarkup
        )

    if (exception != null) {
        println("❌ Ошибка при отправке фото: ${exception.message}")
        return
    }

    // 3) извлекаем TelegramResponse<Message> из Retrofit Response
    val body: TelegramResponse<Message>? = response?.body()
    if (body?.ok == true) {
        val message: Message = body.result
        // сохраняем messageId
        MessageCache.save(chatId, message.messageId)
    } else {
        println("❌ Ошибка API Telegram при sendPhoto: ok=false")
    }
}