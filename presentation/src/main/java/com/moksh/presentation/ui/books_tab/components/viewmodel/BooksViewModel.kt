package com.moksh.presentation.ui.books_tab.components.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor() : ViewModel() {
    private val _bookState = MutableStateFlow(BookState())
    val bookState = _bookState.asStateFlow()
            .onStart {  }
            .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            BookState()
        )

    private val _addBookState = MutableStateFlow(AddBookState())
    val addBookState = _addBookState.asStateFlow()

    private val _bookFlow = MutableSharedFlow<BooksEvent>()
    val bookFlow = _bookFlow.asSharedFlow()

    fun onAction(action: BooksAction){
        when(action){
            is BooksAction.OnNameChange -> onNameChange(action.name)
            BooksAction.OnSaveBook -> onSaveBook()
            BooksAction.ToggleBottomSheet -> toggleBottomSheet()
        }
    }

    private fun toggleBottomSheet() {
        _bookState.update {
            it.copy(
                addNewBottomSheetVisible = !it.addNewBottomSheetVisible
            )
        }
    }

    private fun onNameChange(name: String) {
        _addBookState.update {
            it.copy(
                bookName = name,
            )
        }
    }

    private fun onSaveBook() {
        _addBookState.update {
            it.copy(
                buttonLoading = true,
            )
        }
        toggleBottomSheet()
    }
}