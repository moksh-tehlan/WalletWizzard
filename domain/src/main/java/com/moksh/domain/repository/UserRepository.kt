package com.moksh.domain.repository

import com.moksh.domain.model.request.SaveUserRequest
import com.moksh.domain.model.response.User
import com.moksh.domain.util.DataError
import com.moksh.domain.util.EmptyResult
import com.moksh.domain.util.Result

interface UserRepository {
    suspend fun getUser(): Result<User, DataError>
    suspend fun saveUser(user: SaveUserRequest): EmptyResult<DataError>
    suspend fun updateUser(user: User): EmptyResult<DataError>
}