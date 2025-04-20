package domain.usecase.hidify

import domain.Constants.LANGUAGE_RU
import domain.model.User
import domain.repositories.UserRepository
import domain.usecase.UserUseCase

class UserUseCaseImpl(
    private val userRepository: UserRepository
) : UserUseCase {

    override suspend fun createUser(
        nameUser: String,
        dayLimit: Int,
        telegramId: Int,
        usageLimitGB: Double
    ): User {

        return userRepository.createUser(
            enable = true,
            isActive = true,
            lang = LANGUAGE_RU,
            name = nameUser,
            packageDays = dayLimit,
            telegramId = telegramId,
            usageLimitGB = usageLimitGB
        )


    }

    override suspend fun deleteUser(uuid: String): Boolean {
        return userRepository.deleteUser(uuid)
    }

}