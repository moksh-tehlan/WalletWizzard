package com.moksh.data.repository

import com.moksh.data.datasource.UserDataSource
import com.moksh.data.entities.local.UserEntity
import com.moksh.data.mappers.toDto
import com.moksh.data.mappers.toEntity
import com.moksh.domain.model.request.SaveUserRequest
import com.moksh.domain.model.response.User
import com.moksh.domain.repository.UserRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.EmptyResult
import com.moksh.domain.util.Result
import com.moksh.domain.util.asEmptyDataResult
import com.moksh.domain.util.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource
) : UserRepository {
    override suspend fun getUser(): Result<User, DataError> {
        return userDataSource.getUser().map { userEntity -> userEntity.toDto() }
    }

    override suspend fun saveUser(user: SaveUserRequest): EmptyResult<DataError> {
        val userEntity = UserEntity(
            name = user.name,
            email = user.email,
            phoneNumber = user.phoneNumber
        )
        return userDataSource.saveUser(userEntity).asEmptyDataResult()
    }

    override suspend fun updateUser(user: User): EmptyResult<DataError> {
        return userDataSource.updateUser(user.toEntity()).asEmptyDataResult()
    }
}