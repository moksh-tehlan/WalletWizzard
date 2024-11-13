package com.moksh.presentation.ui.category.viewmodel

import com.moksh.domain.model.response.Category
import com.moksh.domain.model.response.TransactionType

data class CategoryState(
    val categoryDataState: CategoryDataState = CategoryDataState.Loading,
    val selectedCategory: Category? = null,
    val transactionType: TransactionType? = null,
)

sealed interface CategoryDataState{
    data object Loading: CategoryDataState
    data class Success(val categories: List<Category>): CategoryDataState
    data class Error(val error: String): CategoryDataState
}