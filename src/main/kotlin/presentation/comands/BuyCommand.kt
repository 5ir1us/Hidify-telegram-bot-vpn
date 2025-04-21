package presentation.comands

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.handlers.CallbackQueryHandlerEnvironment
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.ReplyMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import domain.Constants.MAX_PAYMENT_AMOUNT
import domain.Constants.MID_PAYMENT_AMOUNT
import domain.Constants.MIN_PAYMENT_AMOUNT
import domain.usecase.CreatePaymentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import presentation.utils.MessageCache
import presentation.utils.PaymentUserCache
import presentation.utils.autoDeleteMessage
import javax.inject.Inject
import javax.inject.Provider

class BuyCommand @Inject constructor(
    val createPaymentUseCase: CreatePaymentUseCase,
    val startCommandProvider: Provider<StartCommand>
) {
    /**
    === Регистрируем команды и коллбэки ===
     */
    fun register(dispatcher: Dispatcher) {

        dispatcher.callbackQuery("buy_1m")
        {
            autoDeleteMessage {
                handleBuyCallback(
                    chatId = callbackQuery.message?.chat?.id,
                    period = "1 месяц\uD83D\uDCA7",
                    price = MIN_PAYMENT_AMOUNT
                )
            }
        }
        dispatcher.callbackQuery("buy_3m")
        {
            autoDeleteMessage {
                handleBuyCallback(
                    chatId = callbackQuery.message?.chat?.id,
                    period = "6 месяцев\uD83D\uDCA6",
                    price = MID_PAYMENT_AMOUNT
                )
            }
        }
        dispatcher.callbackQuery("buy_12m")
        {
            autoDeleteMessage {
            handleBuyCallback(
                chatId = callbackQuery.message?.chat?.id,
                period = "12 месяцев\uD83C\uDF08",
                price = MAX_PAYMENT_AMOUNT
            )
                }
        }
    }
    /**
    === Кнопки  ===
     */
    private fun getBuyMenu(): ReplyMarkup {
        return InlineKeyboardMarkup.create(
            listOf(
                listOf(InlineKeyboardButton.CallbackData("1 месяц\uD83D\uDCA7 - $MIN_PAYMENT_AMOUNT₽", "buy_1m")),
                listOf(InlineKeyboardButton.CallbackData("6 месяцев\uD83D\uDCA6 - $MID_PAYMENT_AMOUNT ₽", "buy_3m")),
                listOf(InlineKeyboardButton.CallbackData("На год\uD83C\uDF08 - $MAX_PAYMENT_AMOUNT ₽", "buy_12m")),
                listOf(InlineKeyboardButton.CallbackData("🔙 Назад", "back_to_start"))
            )
        )
    }
    /**
    === Показываем меню   ===
     */
    fun executeBuyCommand(callbackQueryHandler: CallbackQueryHandlerEnvironment, chatId: Long) {
        callbackQueryHandler.bot.sendMessage(
            chatId = ChatId.fromId(chatId),
            text = "Выберите период подписки:",
            replyMarkup = getBuyMenu()
        ).fold(
            { message -> MessageCache.save(chatId, message.messageId) }, // Сохраняем ID
            { error -> println("Ошибка: $error") }
        )
    }
    /**
    === Обработка создания платежа ===
     */
    private fun CallbackQueryHandlerEnvironment.handleBuyCallback(
        chatId: Long?,
        period: String,
        price: String)
    {
        if (chatId == null) return

        runBlocking(Dispatchers.IO) {
            try {

                val payment = createPaymentUseCase.paymentNew(
                    amount = price,
                    description = "VPN $period для chat_id=$chatId"
                )

                PaymentUserCache.save(payment.id ?: "", chatId)

                val paymentUrl = payment.confirmation?.confirmation_url?.takeIf {
                    it.isNotBlank()
                } ?: payment.confirmation?.returnUrl


                if (paymentUrl == null || paymentUrl.isEmpty()) {
                    bot.sendMessage(
                        chatId = ChatId.fromId(chatId),
                        text = "❌ Произошла ошибка при получении ссылки на оплату. Пожалуйста, попробуйте снова."
                    ).fold(
                        { message -> MessageCache.save(chatId, message.messageId) }, // Сохраняем ID
                        { error -> println("Ошибка: $error") }
                    )
                    return@runBlocking

                } else {
                    val keyboard = InlineKeyboardMarkup.create(
                        listOf(
                            listOf(
                                InlineKeyboardButton.Url(
                                    text = "💳 Оплатить сейчас",
                                    url = paymentUrl
                                )
                            ), listOf( // Второй ряд - кнопка "Назад"
                                InlineKeyboardButton.CallbackData(
                                    text = "🔙 Назад",
                                    callbackData = "back_to_start"
                                )
                            )
                        )
                    )
                    bot.sendMessage(
                        chatId = ChatId.fromId(chatId),
                        text = "🔐 Подписка на $period.\nНажмите на кнопку ниже, чтобы оплатить:",
                        replyMarkup = keyboard
                    ).fold(
                        { message -> MessageCache.save(chatId, message.messageId) }, // Сохраняем ID
                        { error -> println("Ошибка: $error") }
                    )
                }

            } catch (e: Exception) {

            }
        }
    }


}


