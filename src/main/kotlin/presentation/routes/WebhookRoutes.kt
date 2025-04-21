package presentation.routes

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.ChatId
import data.dto.payment.webhook.WebhookPaymentEventDto
import domain.Constants.DATA_PERIOD_MAX
import domain.Constants.DATA_PERIOD_MID
import domain.Constants.DATA_PERIOD_MIN
import domain.Constants.MAX_LIMIT_GB
import domain.Constants.MAX_PAYMENT_AMOUNT
import domain.Constants.MID_LIMIT_GB
import domain.Constants.MID_PAYMENT_AMOUNT
import domain.Constants.MIN_LIMIT_GB
import domain.Constants.MIN_PAYMENT_AMOUNT
import domain.model.SubscriptionParameters
import domain.usecase.ConfigUseCase
import domain.usecase.UserUseCase
import io.ktor.http.*
import io.ktor.server.application.call
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import presentation.utils.PaymentUserCache

fun Routing.webhookRoutes(bot: Bot, userUseCase: UserUseCase, configUseCase: ConfigUseCase) {
    post("/webhooks") {
        PaymentUserCache.clear()
        val rawBody = call.receiveText()
        println("📥 Входящий вебхук: $rawBody")
        try {
            val webhookData = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
                .decodeFromString<WebhookPaymentEventDto>(rawBody)

            when (webhookData.event) {
                "payment.succeeded" -> {
                    val description = webhookData.payment.description ?: ""
                    val amountValue = webhookData.payment.amount.value
                    println("Получена успешная оплата на сумму: $amountValue")

                    val subscriptionParameters = when (amountValue) {
                        MIN_PAYMENT_AMOUNT -> SubscriptionParameters(DATA_PERIOD_MIN, MIN_LIMIT_GB)
                        MID_PAYMENT_AMOUNT -> SubscriptionParameters(DATA_PERIOD_MID, MID_LIMIT_GB)
                        MAX_PAYMENT_AMOUNT -> SubscriptionParameters(DATA_PERIOD_MAX, MAX_LIMIT_GB)
                        else -> {
                            println("⚠️ Сумма $amountValue не соответствует ни одному тарифу")
                            null
                        }
                    }

                    val paymentId = webhookData.payment.id
                    val chatIdRegex = "chat_id=(\\d+)".toRegex()
                    val match = chatIdRegex.find(description)
                    val chatId = match?.groups?.get(1)?.value?.toLongOrNull()

                    // Проверка: если данный paymentId уже обработан, пропускаем логику
                    if (PaymentUserCache.getChatId(paymentId) != null) {
                        PaymentUserCache.clear()
                        println("🔁 Этот paymentId $paymentId уже обработан или отсутствует.")
                        call.respond(HttpStatusCode.OK)
                        return@post
                    }

                    if (chatId != null && subscriptionParameters != null) {
                        PaymentUserCache.save(paymentId, chatId)

                        val user = userUseCase.createUser(
                            nameUser = "Rabbit_$chatId",
                            dayLimit = subscriptionParameters.dayLimit,
                            telegramId = chatId,
                            usageLimitGB = subscriptionParameters.usageLimitGB
                        )
                        println("✅ Новый ключ (пользователь) создан: $user")

                        if (user.uuid == null) {
                            println("⚠️ uuid не установлен")
                            bot.sendMessage(
                                chatId = ChatId.fromId(chatId),
                                text = "🎉 Оплата прошла успешно! Но произошла ошибка при выдаче ключа: отсутствует uuid."
                            )
                        } else {
                            val config = configUseCase.tryGetConfigWithRetry(user.uuid)
                            val vpnKey = config?.full_url ?: "ошибка, обратитесь к администратору"
                            println("vpnkey: $vpnKey")
                            bot.sendMessage(
                                chatId = ChatId.fromId(chatId),
                                text = "🎉 Оплата прошла успешно!\n" +
                                        "Это ВРЕМЕННАЯ ССЫЛКА постоянная в СТАТУСЕ\nВаш VPN-ключ: \n" +
                                        vpnKey
                            )
                            println("VPN-ключ отправлен пользователю (ключ создан) с chat_id=$chatId")
                        }
                        // Отмечаем, что данный paymentId обработан
                        PaymentUserCache.save(paymentId, chatId)

                    } else {
                        println("⚠️ Не удалось извлечь необходимые данные: paymentId=$paymentId, chatId=$chatId")
                    }
                }

                "payment.canceled" -> println("❌ Платеж отменен!")
                "refund.succeeded" -> println("🔄 Возврат денег завершен!")
                else -> println("Получено событие: ${webhookData.event}")
            }
            call.respond(HttpStatusCode.OK)
        } catch (e: Exception) {
            println("Ошибка при обработке вебхука: ${e.message}")
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}





