package presentation

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.logging.LogLevel
import domain.usecase.UserUseCase
import presentation.comands.BuyCommand
import presentation.comands.CancelCommand
import presentation.comands.StartCommand
import presentation.comands.StatusCommand
import javax.inject.Inject


class TelegramBotRoot @Inject constructor(
    private val userUseCase: UserUseCase,
    private val startCommand: StartCommand,
    private val buyCommand: BuyCommand,
    private val statusCommand: StatusCommand,
    private val cancelCommand: CancelCommand
) {

    private val bot = bot {
        token = System.getenv("TELEGRAM_BOT_TOKEN") ?: throw IllegalStateException("TELEGRAM_BOT_TOKEN отсутствует")
        logLevel = LogLevel.Network.Body

        dispatch {
            registerCommands(this)
        }

    }
    fun getBot() = bot
    fun start() {
        println("Запускаем Telegram-бота...")
        bot.startPolling()
    }
    private fun registerCommands(dispatcher: Dispatcher) {
        startCommand.register(dispatcher)
        buyCommand.register(dispatcher)
        statusCommand.register(dispatcher)
        cancelCommand.register(dispatcher)
    }
}