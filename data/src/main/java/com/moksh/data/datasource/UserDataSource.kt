package com.moksh.data.datasource

import com.moksh.data.dao.UserDao
import com.moksh.data.entities.local.UserEntity
import com.moksh.data.entities.utils.safeDbCall
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun getUser(): Result<UserEntity, DataError.Local> = safeDbCall {
        userDao.getUser() ?: throw Exception("User not found")
    }

    suspend fun saveUser(user: UserEntity): Result<Unit, DataError.Local> = safeDbCall {
        userDao.insertUser(user)
    }

    suspend fun updateUser(user: UserEntity): Result<Unit, DataError.Local> = safeDbCall {
        userDao.updateUser(user)
    }
}