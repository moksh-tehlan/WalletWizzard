package com.moksh.data.mappers

import com.moksh.data.entities.local.UserEntity
import com.moksh.domain.model.response.User

fun UserEntity.toUser(): User {
    return User(
        id = id,
        name = name,
        email = email,
        phoneNumber = phoneNumber
    )
}