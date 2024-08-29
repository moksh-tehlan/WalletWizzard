package com.moksh.domain.usecases.user

import com.moksh.domain.model.request.SaveUserRequest
import com.moksh.domain.model.response.User
import com.moksh.domain.repository.UserRepository
import javax.inject.Inject

class SaveUser @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(user: SaveUserRequest) = userRepository.saveUser(
        User(
            phoneNumber = user.phoneNumber,
            name = user.name,
            email = user.email
        )
    )
}