package com.moksh.presentation.ui.category.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moksh.domain.model.response.TransactionType
import com.moksh.domain.usecases.category.GetCategories
import com.moksh.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategories: GetCategories,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState = _categoryState.onStart {
        val entryType = TransactionType.valueOf(savedStateHandle["transactionType"] ?: "Income")
        _categoryState.value = _categoryState.value.copy(
            transactionType = entryType
        )
        getAllCategories(query = "", type = entryType)
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CategoryState()
        )

    fun onAction(action: CategoryAction) {
        when (action) {
            is CategoryAction.GetAllCategories -> getAllCategories(action.query, action.type)
            is CategoryAction.AddNewCategory -> {}
            is CategoryAction.CategorySelected -> {}
        }
    }

    private fun getAllCategories(query: String?, type: TransactionType) {
        viewModelScope.launch {
            when (val result = getCategories.invoke(query = query, type = type)) {
                is Result.Error -> {
                    Timber.d(result.error.name)
                }

                is Result.Success -> {
                    result.data.collectLatest { categories ->
                        _categoryState.update {
                            it.copy(
                                categoryDataState = CategoryDataState.Success(categories)
                            )
                        }
                    }
                }

            }
        }
    }

}