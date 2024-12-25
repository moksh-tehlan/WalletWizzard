package com.moksh.presentation.ui.category.viewmodel

import com.moksh.domain.model.response.Category
import com.moksh.domain.model.response.TransactionType

sealed class CategoryAction {
    data class GetAllCategories(val query: String? = "", val type: TransactionType) :
        CategoryAction()

    data class CategoryChanged(val category: Category?=null) : CategoryAction()
    data class AddNewCategory(val category: Category) : CategoryAction()
    data object OnBackPress : CategoryAction()
    data object ToggleAddNewCategorySheet : CategoryAction()
    data class NewCategoryChanged(val value: String) : CategoryAction()
    data object SaveNewCategory:CategoryAction()
}