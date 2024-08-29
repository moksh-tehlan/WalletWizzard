package com.moksh.domain.usecases.user

import com.moksh.domain.model.response.User
import com.moksh.domain.repository.UserRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Error
import com.moksh.domain.util.Result
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class GetUserTest {

    private lateinit var userRepository: UserRepository
    private lateinit var getUser: GetUser
    private lateinit var expectedUser: User

    @Before
    fun setUp() {
        userRepository = mock(UserRepository::class.java)
        getUser = GetUser(userRepository)
        expectedUser = User(
            id = 1,
            phoneNumber = "7015472985",
            name = "Moksh Tehlan",
            email = "tehlanmoksh@gmail.com",
        )
    }

    @Test
    fun `get user success case`() = runBlocking {
        Mockito.`when`(userRepository.getUser()).thenReturn(Result.Success(expectedUser))
        when (val user = getUser.invoke()) {
            is Result.Success -> assertEquals(expectedUser, user.data)
            is Result.Error -> assert(false)
        }
    }

    @Test
    fun `get user failure case`() = runBlocking {
        Mockito.`when`(userRepository.getUser()).thenReturn(Result.Error(DataError.Local.UNKNOWN))
        when (val response = getUser.invoke()) {
            is Result.Success -> assert(false)
            is Result.Error -> assert(response.error == DataError.Local.UNKNOWN)
        }
    }
}