package di

import dagger.Component
import domain.usecase.ConfigUseCase
import domain.usecase.CreateWebhookUseCase
import domain.usecase.UserUseCase
import presentation.TelegramBotRoot
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class, UIModule::class])
interface AppComponent {
    fun getTelegramBot(): TelegramBotRoot
    fun getCreateWebhookUseCase(): CreateWebhookUseCase
    fun getUserUseCase(): UserUseCase
    fun getConfigUseCase(): ConfigUseCase
}