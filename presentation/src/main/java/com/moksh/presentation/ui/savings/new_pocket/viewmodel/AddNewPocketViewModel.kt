package com.moksh.presentation.ui.savings.new_pocket.viewmodel

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moksh.domain.model.request.InsertSaving
import com.moksh.domain.usecases.savings.CreateSavings
import com.moksh.domain.util.Result
import com.moksh.presentation.core.theme.WizzardCardBlue
import com.moksh.presentation.core.theme.WizzardCardGreen
import com.moksh.presentation.core.theme.WizzardCardPurple
import com.moksh.presentation.core.utils.asUiText
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
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddNewPocketViewModel @Inject constructor(
    private val createSavings: CreateSavings,
) : ViewModel() {
    private val _addNewPocketState = MutableStateFlow(AddNewPocketState())
    val addNewPocketState = _addNewPocketState.asStateFlow()
        .onStart { }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            AddNewPocketState()
        )

    private val _addNewPocketFlow = MutableSharedFlow<AddNewPocketEvent>()
    val addNewPocketFlow = _addNewPocketFlow.asSharedFlow()

    fun onAction(action: AddNewPocketAction) {
        when (action) {
            is AddNewPocketAction.OnPocketNameChange -> onPocketNameChange(action.name)
            is AddNewPocketAction.OnAddNewPocket -> onAddNewPocket()
            is AddNewPocketAction.OnAmountChange -> onAmountChange(action.amount)
            is AddNewPocketAction.OnDateChange -> onDateChange(action.date)
            is AddNewPocketAction.OnToggleDatePicker -> onToggleDatePicker()
        }
    }

    private fun onToggleDatePicker() {
        _addNewPocketState.update {
            it.copy(
                showDatePicker = !it.showDatePicker
            )
        }
    }

    private fun onDateChange(date: Date) {
        _addNewPocketState.update {
            it.copy(date = date)
        }
    }

    private fun onAmountChange(amount: String) {
        // If empty, set to empty and return
        if (amount.isEmpty()) {
            _addNewPocketState.update { it.copy(amount = amount) }
            return
        }

        // Check if the input is a valid number format
        val isValidNumberFormat = amount.matches(Regex("^-?\\d*\\.?\\d*$"))
        if (!isValidNumberFormat) return

        // Try parsing to Double to check range
        try {
            amount.toDouble() // Just to validate if it's in Double range
            _addNewPocketState.update { it.copy(amount = amount) }
        } catch (e: NumberFormatException) {
            // If number is too large/small for Double, keep previous value
            return
        }
    }

    private fun onAddNewPocket() {
        _addNewPocketState.update {
            it.copy(isButtonLoading = true)
        }
        viewModelScope.launch {
            val savePocket = InsertSaving(
                name = addNewPocketState.value.pocketName,
                targetAmount = addNewPocketState.value.amount.toDouble(),
                endDate = addNewPocketState.value.date,
                progressBarColor = getRandomColor()
            )

            when(val result = createSavings.invoke(savePocket)){
                is Result.Success ->{
                    _addNewPocketState.update {
                        it.copy(
                            isButtonLoading = false,
                            error = null
                        )
                    }

                    _addNewPocketFlow.emit(AddNewPocketEvent.CreatePocketSuccess)
                }
                is Result.Error -> {
                    _addNewPocketState.update {
                        it.copy(
                            isButtonLoading = false,
                            error = result.error.asUiText()
                        )
                    }
                }
            }
        }

    }

    private fun getRandomColor(): Int {
        val colorList = listOf(WizzardCardBlue, WizzardCardGreen, WizzardCardPurple)
        return colorList.random().toArgb()
    }

    private fun onPocketNameChange(pocketName: String) {
        _addNewPocketState.update {
            it.copy(
                pocketName = pocketName
            )
        }
    }
}