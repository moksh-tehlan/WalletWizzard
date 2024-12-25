package com.moksh.domain.usecases.auth

import com.moksh.domain.repository.AuthRepository
import javax.inject.Inject

class SendOtp @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(phoneNumber:String) = authRepository.sendOtp(phoneNumber)
}