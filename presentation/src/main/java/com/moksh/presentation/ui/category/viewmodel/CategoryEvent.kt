package com.moksh.presentation.ui.category.viewmodel

import com.moksh.domain.model.response.Category

sealed interface CategoryEvent {
    data object OnBackPress: CategoryEvent
    data class OnCategoryChanged(val category: Category?=null): CategoryEvent
}