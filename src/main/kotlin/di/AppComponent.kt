package di

import dagger.Component
import presentation.TelegramBotRoot
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class, UIModule::class])
interface AppComponent {
    fun inject(telegramBotRoot: TelegramBotRoot)
}