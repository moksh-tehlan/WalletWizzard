package com.moksh.domain.repository

import com.moksh.domain.model.response.User
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result

interface UserRepository {
    suspend fun getUser(): Result<User, DataError>
    suspend fun saveUser(user: User): Result<User, DataError>
    suspend fun updateUser(user: User): Result<User, DataError>
}