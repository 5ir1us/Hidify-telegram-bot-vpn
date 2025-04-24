package di

import dagger.Module
import dagger.Provides
import domain.repositories.ConfigRepository
import domain.repositories.PaymentRepository
import domain.repositories.UserRepository
import domain.usecase.UserUseCase
import domain.usecase.ConfigUseCase
import domain.usecase.CreatePaymentUseCase
import domain.usecase.hidify.ConfigUseCaseImpl
import domain.usecase.hidify.UserUseCaseImpl
import domain.usecase.payment.CreatePaymentUseCaseImpl
import javax.inject.Singleton

@Module
class DomainModule {


    @Provides
    @Singleton
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase {
        return UserUseCaseImpl(userRepository)
    }

    @Provides
    @Singleton
    fun provideConfigUseCase(configRepository: ConfigRepository): ConfigUseCase {
        return ConfigUseCaseImpl(configRepository)
    }

    @Provides
    @Singleton
    fun provideCreatePaymentUseCase(paymentRepository: PaymentRepository): CreatePaymentUseCase {
        return CreatePaymentUseCaseImpl(paymentRepository)
    }

}