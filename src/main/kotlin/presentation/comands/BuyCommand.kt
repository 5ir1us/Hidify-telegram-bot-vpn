package presentation.comands

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
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
import presentation.routes.PaymentUserCache
import javax.inject.Inject
import javax.inject.Provider

class BuyCommand @Inject constructor(
    val createPaymentUseCase: CreatePaymentUseCase,
    val startCommandProvider: Provider<StartCommand>
) {


    fun register(dispatcher: Dispatcher) {
        dispatcher.command("buy") {
            val chatId = message.chat.id
            bot.sendMessage(
                chatId = ChatId.fromId(chatId),
                text = "Выберите период подписки:",
                replyMarkup = getBuyMenu()
            )
        }
        dispatcher.callbackQuery("buy_1m")
        {
            handleBuyCallback(chatId = callbackQuery.message?.chat?.id, period = "1 месяц", price = MIN_PAYMENT_AMOUNT)
            println("handleBuyCallback вызван")

        }
        dispatcher.callbackQuery("buy_3m")
        {
            handleBuyCallback(chatId = callbackQuery.message?.chat?.id, period = "6 месяцев", price = MID_PAYMENT_AMOUNT)
            println("handleBuyCallback вызван")
        }
        dispatcher.callbackQuery("buy_12m")
        {
            handleBuyCallback(chatId = callbackQuery.message?.chat?.id, period = "12 месяцев", price = MAX_PAYMENT_AMOUNT)
            println("handleBuyCallback вызван")
        }

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
                listOf(InlineKeyboardButton.CallbackData("1 месяц - $MIN_PAYMENT_AMOUNT₽", "buy_1m")),
                listOf(InlineKeyboardButton.CallbackData("3 месяца - $MID_PAYMENT_AMOUNT ₽", "buy_3m")),
                listOf(InlineKeyboardButton.CallbackData("На год  - $MAX_PAYMENT_AMOUNT ₽", "buy_12m")),
                listOf(InlineKeyboardButton.CallbackData("🔙 Назад", "back_to_start"))
            )
        )
    }

    private fun CallbackQueryHandlerEnvironment.handleBuyCallback(chatId: Long?, period: String, price: String) {
        if (chatId == null) return
        println("chatId: $chatId")

        runBlocking(Dispatchers.IO) {
            try {

                println("Запрос на создание платежа отправлен...")

                val payment = createPaymentUseCase.paymentNew(
                    amount = price,
                    description = "VPN $period для chat_id=$chatId"
                )

                println("Ответ от YooKassa получен: $payment")
                PaymentUserCache.save(payment.id ?: "", chatId)

                val paymentUrl = payment.confirmation?.confirmation_url?.takeIf { it.isNotBlank() }
                    ?: payment.confirmation?.returnUrl
                println("paymentUrl: $paymentUrl")

                if (paymentUrl == null || paymentUrl.isEmpty()) {
                    bot.sendMessage(
                        chatId = ChatId.fromId(chatId),
                        text = "❌ Произошла ошибка при получении ссылки на оплату. Пожалуйста, попробуйте снова."
                    )
                    return@runBlocking
                } else {
                    bot.sendMessage(
                        chatId = ChatId.fromId(chatId),
                        text = "Оплата: Перейдите по ссылке для оплаты)) $paymentUrl"
                    )
                }
                println("Ссылка на оплату отправлена пользователю: $paymentUrl")

            } catch (e: Exception) {
                println("Ошибка при обработке платежа: ${e.message}")
            }
        }
    }


}


