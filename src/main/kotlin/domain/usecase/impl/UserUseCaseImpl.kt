package domain.usecase.impl

import domain.model.User
import domain.repositories.UserRepository
import domain.usecase.UserUseCase

class UserUseCaseImpl(
    private val userRepository: UserRepository
) : UserUseCase {
    override suspend fun createUser(user: User) {
        userRepository.createUser(user)

    }

    override suspend fun deleteUser(uuid: String): Boolean {
        return userRepository.deleteUser(uuid)
    }


}