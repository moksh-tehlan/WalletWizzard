package com.moksh.presentation.ui.books_tab.components.viewmodel

sealed class BooksEvent {
    data object OnBackPress : BooksEvent()
}