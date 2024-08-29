package com.moksh.data.mappers

import com.moksh.data.entities.local.CategoryEntity
import com.moksh.domain.model.response.Category

fun CategoryEntity.toCategory(): Category {
    return Category(
        id,
        name
    )
}