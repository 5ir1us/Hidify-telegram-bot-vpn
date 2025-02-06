package di

import dagger.Module
import dagger.Provides
import domain.usecase.ConfigUseCase
import domain.usecase.UserUseCase
import presentation.comands.BuyCommand
import presentation.comands.CancelCommand
import presentation.comands.StartCommand
import presentation.comands.StatusCommand
import javax.inject.Singleton

@Module
class UIModule {

    @Provides
    @Singleton
    fun provideStartCommand(buyCommand: BuyCommand, statusCommand: StatusCommand): StartCommand {
        return StartCommand(buyCommand,statusCommand)
    }

    @Provides
    @Singleton
    fun provideBuyCommand(): BuyCommand {
        return BuyCommand()
    }

    @Provides
    @Singleton
    fun provideStatusCommand(configUseCase: ConfigUseCase): StatusCommand {
        return StatusCommand(configUseCase)
    }

    @Provides
    @Singleton
    fun provideCancelCommand(): CancelCommand {
        return CancelCommand()
    }
}