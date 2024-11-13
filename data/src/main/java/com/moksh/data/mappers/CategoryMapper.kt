package com.moksh.data.mappers

import com.moksh.data.entities.local.CategoryEntity
import com.moksh.domain.model.response.Category

object CategoryMapper {
    fun toDto(entity: CategoryEntity) = Category(
        id = entity.id,
        name = entity.name,
        icon = entity.icon,
        color = entity.color,
        type = entity.type,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt
    )

    fun toEntity(dto: Category) = CategoryEntity(
        id = dto.id,
        name = dto.name,
        icon = dto.icon,
        color = dto.color,
        type = dto.type,
        createdAt = dto.createdAt,
        updatedAt = dto.updatedAt
    )
}

fun CategoryEntity.toDto() = CategoryMapper.toDto(this)
fun Category.toEntity() = CategoryMapper.toEntity(this)