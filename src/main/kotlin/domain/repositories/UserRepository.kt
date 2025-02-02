package domain.repositories

 import domain.model.User

interface UserRepository {
    suspend fun createUser(user: User)
    suspend fun deleteUser(uuid: String): Boolean
}