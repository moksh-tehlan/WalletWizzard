package com.moksh.presentation.ui.books_tab.components.viewmodel

sealed class BooksAction {
    data class OnNameChange(val name: String) : BooksAction()
    data object ToggleBottomSheet : BooksAction()
    data object OnSaveBook : BooksAction()
}