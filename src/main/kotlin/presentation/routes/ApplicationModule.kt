package presentation.routes

import com.github.kotlintelegrambot.Bot
import domain.usecase.ConfigUseCase
import domain.usecase.UserUseCase
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing

fun Application.module(bot: Bot,userUseCase: UserUseCase, configUseCase: ConfigUseCase) {
    install(ContentNegotiation) {
        json()
    }
    launchPeriodicCacheCleanup()

    routing {
        webhookRoutes(bot,userUseCase,configUseCase) // Регистрируем маршрут для обработки вебхуков
    }
}