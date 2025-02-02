package domain.usecase

import domain.model.User

interface UserUseCase {
 suspend fun createUser(user: User)
 suspend fun deleteUser(uuid: String): Boolean
 }