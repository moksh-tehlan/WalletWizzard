package com.moksh.domain.model.request

import com.moksh.domain.model.response.Category
import com.moksh.domain.model.response.TransactionType

data class SaveCategoryRequest(
    val name: String,
    val icon: String? = null,
    val color: String? = null,
    val type: TransactionType,
)