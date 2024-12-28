package com.moksh.data.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.moksh.data.database.WizzardDatabase
import com.moksh.data.entities.local.UserEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: WizzardDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            WizzardDatabase::class.java
        ).build()
        userDao = database.userDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndGetUser() = runTest {
        // Given
        val user = UserEntity(
            name = "John Doe",
            email = "john@example.com",
            phoneNumber = "+1234567890"
        )

        // When
        userDao.insertUser(user)

        // Then
        val loadedUser = userDao.getUser()
        assertThat(loadedUser).isNotNull()
        assertThat(loadedUser?.name).isEqualTo("John Doe")
        assertThat(loadedUser?.email).isEqualTo("john@example.com")
        assertThat(loadedUser?.phoneNumber).isEqualTo("+1234567890")
    }

    @Test
    fun getUser_whenDatabaseEmpty_returnsNull() = runTest {
        // When
        val user = userDao.getUser()

        // Then
        assertThat(user).isNull()
    }

    @Test
    fun updateUser() = runTest {
        // Given
        val user = UserEntity(
            name = "John Doe",
            email = "john@example.com",
            phoneNumber = "+1234567890"
        )
        userDao.insertUser(user)

        // When
        val updatedUser = user.copy(
            name = "John Updated",
            email = "john.updated@example.com"
        )
        userDao.updateUser(updatedUser)

        // Then
        val loadedUser = userDao.getUser()
        assertThat(loadedUser?.name).isEqualTo("John Updated")
        assertThat(loadedUser?.email).isEqualTo("john.updated@example.com")
        assertThat(loadedUser?.id).isEqualTo(user.id)
    }

    @Test
    fun deleteUser() = runTest {
        // Given
        val user = UserEntity(
            name = "John Doe",
            email = "john@example.com",
            phoneNumber = "+1234567890"
        )
        userDao.insertUser(user)

        // When
        userDao.deleteUser(user)

        // Then
        val loadedUser = userDao.getUser()
        assertThat(loadedUser).isNull()
    }

    @Test
    fun insertUserWithNullableFields() = runTest {
        // Given
        val user = UserEntity(
            name = null,
            email = null,
            phoneNumber = "+1234567890"
        )

        // When
        userDao.insertUser(user)

        // Then
        val loadedUser = userDao.getUser()
        assertThat(loadedUser).isNotNull()
        assertThat(loadedUser?.name).isNull()
        assertThat(loadedUser?.email).isNull()
        assertThat(loadedUser?.phoneNumber).isEqualTo("+1234567890")
    }
}