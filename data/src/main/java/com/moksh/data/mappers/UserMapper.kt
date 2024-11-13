package com.moksh.data.mappers

import com.moksh.data.entities.local.UserEntity
import com.moksh.domain.model.response.User

object UserMapper {
    fun toDto(entity: UserEntity) = User(
        id = entity.id,
        name = entity.name,
        email = entity.email,
        phoneNumber = entity.phoneNumber
    )

    fun toEntity(dto: User) = UserEntity(
        id = dto.id,
        name = dto.name,
        email = dto.email,
        phoneNumber = dto.phoneNumber
    )
}

fun UserEntity.toDto() = UserMapper.toDto(this)
fun User.toEntity() = UserMapper.toEntity(this)