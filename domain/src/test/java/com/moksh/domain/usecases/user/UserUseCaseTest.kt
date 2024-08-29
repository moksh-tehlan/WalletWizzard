package com.moksh.domain.usecases.user

import com.moksh.domain.model.request.SaveUserRequest
import com.moksh.domain.model.request.UpdateUserRequest
import com.moksh.domain.model.response.User
import com.moksh.domain.repository.UserRepository
import com.moksh.domain.util.Result
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.any

class UserUseCaseTest {

    lateinit var userRepository: UserRepository
    lateinit var saveUser: SaveUser
    lateinit var getUser: GetUser
    lateinit var updateUser: UpdateUser

    @Before
    fun setUp() {
        userRepository = mock(UserRepository::class.java)
        saveUser = SaveUser(userRepository)
        getUser = GetUser(userRepository)
        updateUser = UpdateUser(userRepository)
    }

    @Test
    fun saveUser() = runBlocking {
        val user = SaveUserRequest(
            phoneNumber = "7015472985",
            name = "Moksh Tehlan",
            email = "tehlanmoksh@gmail.com"
        )

        val expectedUser = User(
            id = 1,
            phoneNumber = "7015472985",
            name = "Moksh Tehlan",
            email = "tehlanmoksh@gmail.com"
        )

        Mockito.`when`(userRepository.saveUser(any())).thenReturn(
            Result.Success(
                User(
                    id = 1,
                    phoneNumber = user.phoneNumber,
                    name = user.name,
                    email = user.email
                )
            )
        )

        val savedUserResponse = saveUser.invoke(
            user = user
        )

        when (savedUserResponse) {
            is Result.Success -> {
                assertEquals(expectedUser, savedUserResponse.data)
            }

            is Result.Error -> {
                assert(false)
            }
        }
    }

    @Test
    fun getUser() = runBlocking {

        val expectedUser = User(
            id = 1,
            phoneNumber = "7015472985",
            name = "Moksh Tehlan",
            email = "tehlanmoksh@gmail.com"
        )

        Mockito.`when`(userRepository.getUser()).thenReturn(
            Result.Success(
                expectedUser
            )
        )

        when (val userResponse = getUser.invoke()) {
            is Result.Success -> {
                assertEquals(expectedUser, userResponse.data)
            }

            is Result.Error -> {
                assert(false)
            }
        }
    }

    @Test
    fun updateUser() = runBlocking {
        val user = UpdateUserRequest(
            name = "Moksh Tehlan",
            email = "tehlanmoksh@gmail.com"
        )

        val expectedUser = User(
            id = 1,
            phoneNumber = "7015472985",
            name = "Moksh Tehlan",
            email = "tehlanmoksh@gmail.com"
        )

        Mockito.`when`(userRepository.getUser()).thenReturn(
            Result.Success(
                expectedUser
            )
        )
        Mockito.`when`(userRepository.updateUser(any())).thenReturn(
            Result.Success(
                User(
                    id = expectedUser.id,
                    phoneNumber = expectedUser.phoneNumber,
                    name = user.name,
                    email = user.email
                )
            )
        )

        val updateUser = updateUser.invoke(
            user = user
        )

        when (updateUser) {
            is Result.Success -> {
                assertEquals(expectedUser, updateUser.data)
            }

            is Result.Error -> {
                assert(false)
            }
        }
    }
}