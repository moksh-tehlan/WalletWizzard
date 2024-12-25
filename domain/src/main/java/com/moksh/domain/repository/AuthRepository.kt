package com.moksh.domain.repository

import com.moksh.domain.util.DataError
import com.moksh.domain.util.EmptyResult
import com.moksh.domain.util.Result

interface AuthRepository {
    suspend fun sendOtp(phoneNumber: String): Result<String, DataError>
    suspend fun verifyOtp(
        phoneNumber: String,
        verificationId: String,
        code: String
    ): EmptyResult<DataError>
}