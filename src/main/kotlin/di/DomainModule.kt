package di

import dagger.Module
import dagger.Provides
import domain.repositories.ConfigRepository
import domain.repositories.UserRepository
import domain.usecase.UserUseCase
import domain.usecase.ConfigUseCase
import domain.usecase.impl.ConfigUseCaseImpl
import domain.usecase.impl.UserUseCaseImpl
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
}