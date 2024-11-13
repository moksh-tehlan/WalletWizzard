package com.moksh.domain.usecases.user

import com.moksh.domain.model.request.UpdateUserRequest
import com.moksh.domain.repository.UserRepository
import com.moksh.domain.util.Result
import com.moksh.domain.util.asEmptyDataResult
import javax.inject.Inject

class UpdateUser @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        user: UpdateUserRequest
    ) = when (val oldUser = userRepository.getUser()) {
        is Result.Success -> {
            val updatedUser = oldUser.data?.copy(
                name = user.name,
                email = user.email,
            )
            if (updatedUser != null)
                userRepository.updateUser(updatedUser)
            oldUser.asEmptyDataResult()
        }

        is Result.Error -> oldUser
    }
}