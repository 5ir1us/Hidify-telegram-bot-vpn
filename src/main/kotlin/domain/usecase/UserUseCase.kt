package domain.usecase

import domain.model.User

interface UserUseCase {
 suspend fun createUser(
  nameUser: String,
  dayLimit: Int,
  telegramId: Long,
  usageLimitGB: Double
 ):User
 
 suspend fun deleteUser(uuid: String): Boolean
 }