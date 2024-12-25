package com.moksh.presentation.ui.category.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moksh.domain.model.request.SaveCategoryRequest
import com.moksh.domain.model.response.Category
import com.moksh.domain.model.response.TransactionType
import com.moksh.domain.usecases.category.GetCategories
import com.moksh.domain.usecases.category.SaveCategory
import com.moksh.domain.util.Result
import com.moksh.presentation.core.utils.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
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
    private val saveCategory: SaveCategory,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState = _categoryState.onStart {
        val entryType = TransactionType.valueOf(savedStateHandle["transactionType"] ?: "Income")
        val selectedCategoryId = savedStateHandle.get<String>("categoryId")

        _categoryState.value = _categoryState.value.copy(
            transactionType = entryType
        )
        updateSelectedCategory(categoryId = selectedCategoryId)
        getAllCategories(query = "", type = entryType)
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CategoryState()
        )

    private val _categoryEvent = MutableSharedFlow<CategoryEvent>()
    val categoryEvent = _categoryEvent.asSharedFlow()

    fun onAction(action: CategoryAction) {
        when (action) {
            is CategoryAction.GetAllCategories -> getAllCategories(action.query, action.type)
            is CategoryAction.AddNewCategory -> {}
            is CategoryAction.CategoryChanged -> onCategoryChanged(action.category)
            is CategoryAction.OnBackPress -> onBackPress()
            is CategoryAction.ToggleAddNewCategorySheet -> onToggleAddNewCategorySheet()
            is CategoryAction.NewCategoryChanged -> onNewCategoryChange(action.value)
            is CategoryAction.SaveNewCategory -> onSaveNewCategory()
        }
    }

    private fun onSaveNewCategory() {
        viewModelScope.launch {
            _categoryState.update {
                it.copy(
                    addNewCategorySheet = it.addNewCategorySheet?.copy(
                        isLoading = true,
                    )
                )
            }
            val result = saveCategory.invoke(
                saveCategoryRequest = SaveCategoryRequest(
                    name = _categoryState.value.addNewCategorySheet?.value ?: "",
                    type = _categoryState.value.transactionType ?: TransactionType.Income
                )
            )
            when (result) {
                is Result.Success -> {
                    onToggleAddNewCategorySheet()
                }

                is Result.Error -> {
                    Timber.d(result.error.name)
                }
            }
            _categoryState.update {
                it.copy(
                    addNewCategorySheet = it.addNewCategorySheet?.copy(
                        isLoading = false,
                    )
                )
            }
        }
    }

    private fun updateSelectedCategory(categoryId: String?) {
        if (categoryId.isNullOrEmpty()) return;
        viewModelScope.launch {
            when (val result = getCategories.invoke(categoryId)) {
                is Result.Success -> {
                    _categoryState.update {
                        it.copy(
                            selectedCategory = result.data
                        )
                    }
                }

                is Result.Error -> {
                    Timber.d("Error: ${result.error.asUiText().asString(context)}")
                }
            }
        }
    }

    private fun onNewCategoryChange(value: String) {
        _categoryState.update {
            it.copy(
                addNewCategorySheet = it.addNewCategorySheet?.copy(
                    value = value,
                )
            )
        }
    }

    private fun onToggleAddNewCategorySheet() {
        _categoryState.update {
            it.copy(
                addNewCategorySheet = if (it.addNewCategorySheet == null) AddNewCategoryBottomSheetState() else null
            )
        }
    }

    private fun onBackPress() {
        viewModelScope.launch {
            _categoryEvent.emit(
                CategoryEvent.OnBackPress
            )
        }
    }

    private fun onCategoryChanged(category: Category?) {
        _categoryState.update {
            it.copy(selectedCategory = if (it.selectedCategory == category) null else category)
        }
        viewModelScope.launch {
            _categoryEvent.emit(
                value = CategoryEvent.OnCategoryChanged(category)
            )
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