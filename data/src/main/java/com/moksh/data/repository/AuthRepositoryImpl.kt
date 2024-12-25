package com.moksh.data.repository

import com.moksh.data.datasource.AuthDataSource
import com.moksh.domain.repository.AuthRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.EmptyResult
import com.moksh.domain.util.Result
import com.moksh.domain.util.asEmptyDataResult
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun sendOtp(phoneNumber: String): Result<String, DataError> {
        return authDataSource.sendOtp(phoneNumber)
    }

    override suspend fun verifyOtp(
        phoneNumber: String,
        verificationId: String,
        code: String
    ): EmptyResult<DataError> {
        return authDataSource.verifyOtp(phoneNumber, verificationId, code).asEmptyDataResult()
    }
}