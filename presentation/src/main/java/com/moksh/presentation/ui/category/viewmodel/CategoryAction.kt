package com.moksh.presentation.ui.category.viewmodel

import com.moksh.domain.model.response.Category
import com.moksh.domain.model.response.TransactionType

sealed class CategoryAction {
    data class GetAllCategories(val query: String? = "", val type: TransactionType) : CategoryAction()
    data class CategorySelected(val category: Category) : CategoryAction()
    data class AddNewCategory(val category: Category) : CategoryAction()
}