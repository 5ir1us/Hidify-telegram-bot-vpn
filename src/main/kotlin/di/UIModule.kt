package di

import dagger.Module
import dagger.Provides
import domain.repositories.PaymentRepository
import domain.usecase.ConfigUseCase
import domain.usecase.CreatePaymentUseCase
import domain.usecase.CreateWebhookUseCase
import domain.usecase.payment.CreateWebhookUseCaseImpl
import presentation.comands.BuyCommand
import presentation.comands.InfoCommand
import presentation.comands.StartCommand
import presentation.comands.StatusCommand
import javax.inject.Provider
import javax.inject.Singleton

@Module
class UIModule {

    @Provides
    @Singleton
    fun provideStartCommand(
        buyCommand:  BuyCommand,
        statusCommand: StatusCommand,
    ): StartCommand {
        return StartCommand(buyCommand, statusCommand)
    }

    @Provides
    @Singleton
    fun provideBuyCommand(
        startCommand: Provider<StartCommand>,
        createPaymentUseCase: CreatePaymentUseCase
    ): BuyCommand {
        return BuyCommand(createPaymentUseCase, startCommand)
    }

    @Provides
    @Singleton
    fun provideStatusCommand(configUseCase: ConfigUseCase): StatusCommand {
        return StatusCommand(configUseCase)
    }

    @Provides
    @Singleton
    fun provideCancelCommand(): InfoCommand {
        return InfoCommand()
    }

    /**
     * @param provideCreateWebhookUseCase  Webhook
     */
    @Provides
    @Singleton
    fun provideCreateWebhookUseCase(paymentRepository: PaymentRepository): CreateWebhookUseCase {
        return CreateWebhookUseCaseImpl(paymentRepository)
    }

}