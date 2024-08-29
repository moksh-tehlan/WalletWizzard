package com.moksh.data.repository

import com.moksh.data.datasource.UserDataSource
import com.moksh.data.entities.local.UserEntity
import com.moksh.data.mappers.toUser
import com.moksh.domain.model.response.User
import com.moksh.domain.repository.UserRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import com.moksh.domain.util.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getUser(): Result<User, DataError> {
        val user = userDataSource.getUser().toUser()
        return Result.Success(
            data = user
        )
    }

    override suspend fun saveUser(user: User): Result<User, DataError> {
        return userDataSource.saveUser(UserEntity.fromUser(user)).map { userEntity ->
            userEntity.toUser()
        }
    }

    override suspend fun updateUser(user: User): Result<User, DataError> {
        return userDataSource.updateUser(UserEntity.fromUser(user)).map { userEntity ->
            userEntity.toUser()
        }
    }
}