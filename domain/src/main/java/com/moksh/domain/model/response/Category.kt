package com.moksh.domain.model.response

import java.util.Date

data class Category(
    val id: String,
    val name: String,
    val icon: String? = null,
    val color: String? = null,
    val type: TransactionType,
    val createdAt: Date? = null,
    val updatedAt: Date? = null
)