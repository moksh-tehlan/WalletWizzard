package com.moksh.domain.usecases.user

import com.moksh.domain.model.request.UpdateUserRequest
import com.moksh.domain.model.response.User
import com.moksh.domain.repository.UserRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import javax.inject.Inject

class UpdateUser @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        user: UpdateUserRequest
    ): Result<User, DataError> {
        return when (val oldUser = userRepository.getUser()) {
            is Result.Success -> {
                val updatedUser = oldUser.data.copy(
                    name = user.name,
                    email = user.email,
                )
                userRepository.updateUser(updatedUser)
            }

            is Result.Error -> oldUser
        }
    }
}