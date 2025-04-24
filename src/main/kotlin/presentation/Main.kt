package presentation

import di.DaggerAppComponent
 import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import presentation.routes.module


fun main() = runBlocking {
    /**
     * Получаем зависимости через Dagger
     */
    val appComponent = DaggerAppComponent.create()
    val telegramBotRoot = appComponent.getTelegramBot()
    val bot = telegramBotRoot.getBot()
    val userUseCase = appComponent.getUserUseCase()
    val configUseCase = appComponent.getConfigUseCase()

    launch {
        embeddedServer(Netty, host = "0.0.0.0", port = 8080) {
            module(bot,userUseCase,configUseCase)
        }.start(wait = true)
    }


    /**
     * Запускаем Telegram-бота
     */
    telegramBotRoot.start()
}
