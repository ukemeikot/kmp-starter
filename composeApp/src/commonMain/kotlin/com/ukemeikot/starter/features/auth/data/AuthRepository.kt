package com.ukemeikot.starter.features.auth.data

import com.ukemeikot.starter.features.auth.data.local.AuthPreferences
import com.ukemeikot.starter.features.auth.data.local.UserDao
import com.ukemeikot.starter.features.auth.data.local.UserEntity
import com.ukemeikot.starter.features.auth.data.remote.AuthApi
import com.ukemeikot.starter.features.auth.data.remote.dto.LoginRequest
import com.ukemeikot.starter.features.auth.data.remote.dto.RegisterRequest
import com.ukemeikot.starter.features.auth.domain.model.User

class AuthRepository(
    private val api: AuthApi,
    private val preferences: AuthPreferences,
    private val userDao: UserDao,
) {

    suspend fun login(email: String, password: String): User {
        val response = api.login(request = LoginRequest(email = email, password = password))
        preferences.saveSession(token = response.accessToken, userId = response.user.id)
        val entity = UserEntity(
            id = response.user.id,
            email = response.user.email,
            name = response.user.name,
        )
        userDao.upsert(user = entity)
        return entity.toDomain()
    }

    suspend fun register(name: String, email: String, password: String): User {
        val response = api.register(
            request = RegisterRequest(name = name, email = email, password = password),
        )
        preferences.saveSession(token = response.accessToken, userId = response.user.id)
        val entity = UserEntity(
            id = response.user.id,
            email = response.user.email,
            name = response.user.name,
        )
        userDao.upsert(user = entity)
        return entity.toDomain()
    }

    suspend fun getCurrentUser(): User? {
        val userId = preferences.getUserId() ?: return null
        return userDao.findById(id = userId)?.toDomain()
    }

    suspend fun logout() {
        preferences.clearSession()
        userDao.deleteAll()
    }

    private fun UserEntity.toDomain() = User(id = id, email = email, name = name)
}
