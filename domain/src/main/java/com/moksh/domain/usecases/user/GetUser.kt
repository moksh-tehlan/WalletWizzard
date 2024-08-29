package com.moksh.domain.usecases.user

import com.moksh.domain.repository.UserRepository
import javax.inject.Inject

class GetUser @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getUser()
}