package com.moksh.domain.usecases.auth

import com.moksh.domain.repository.AuthRepository
import javax.inject.Inject

class VerifyOtp @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        phoneNumber: String,
        verificationId: String,
        code: String
    ) = authRepository.verifyOtp(phoneNumber, verificationId, code)
}