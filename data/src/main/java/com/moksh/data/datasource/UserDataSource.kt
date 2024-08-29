package com.moksh.data.datasource

import com.moksh.data.dto.UserDao
import com.moksh.data.entities.local.UserEntity
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun getUser() = userDao.getUser()
    suspend fun saveUser(user: UserEntity): Result<UserEntity, DataError.Local> {
        return try {
            userDao.insertUser(user)
            Result.Success(getUser())
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    suspend fun updateUser(user: UserEntity): Result<UserEntity, DataError.Local> {
        return try {
            userDao.updateUser(user)
            Result.Success(getUser())
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}