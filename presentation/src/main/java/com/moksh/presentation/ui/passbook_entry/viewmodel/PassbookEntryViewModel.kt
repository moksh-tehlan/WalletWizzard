package com.moksh.presentation.ui.passbook_entry.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.moksh.domain.model.response.PaymentMode
import com.moksh.domain.model.response.SaveTransaction
import com.moksh.domain.model.response.TransactionType
import com.moksh.domain.usecases.category.GetCategories
import com.moksh.domain.usecases.expense.SaveExpense
import com.moksh.domain.usecases.income.SaveIncome
import com.moksh.domain.usecases.payment_mode.GetPaymentModes
import com.moksh.domain.util.Result
import com.moksh.presentation.core.utils.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PassbookEntryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val saveIncome: SaveIncome,
    private val saveExpense: SaveExpense,
    private val paymentModes: GetPaymentModes,
    private val getCategories: GetCategories,
    private val getPayment:GetPaymentModes,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _passbookEntryState = MutableStateFlow(PassbookEntryState())
    val passbookEntryState = _passbookEntryState.asStateFlow()

    private val _passbookEvent = MutableSharedFlow<PassbookEntryEvent>()
    val passbookEvent = _passbookEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            val entryType = TransactionType.valueOf(savedStateHandle["entryType"] ?: "Income")
            Timber.tag("EntryType").d(entryType.toString())
            _passbookEntryState.value = _passbookEntryState.value.copy(
                entryType = entryType,
            )

            // Launch payment modes collection in a separate coroutine
            launch {
                when (val result = paymentModes.invoke()) {
                    is Result.Error -> {
                        Timber.e("Failed to fetch payment modes error : ${result.error.asUiText()}")
                        // Handle error appropriately
                    }

                    is Result.Success -> {
                        result.data.collect { paymentModeList ->
                            _passbookEntryState.value = _passbookEntryState.value.copy(
                                paymentModeList = paymentModeList
                            )
                        }
                    }
                }
            }
        }
    }

    fun onAction(action: PassbookEntryAction) {
        when (action) {
            is PassbookEntryAction.AmountChanged -> amountChanged(action.amount)
            is PassbookEntryAction.OnCategoryChange -> onCategoryChange()
            is PassbookEntryAction.DateChanged -> dateChanged(action.date)
            is PassbookEntryAction.PaymentModeChanged -> paymentModeChanged(action.paymentMode)
            is PassbookEntryAction.RemarkChanged -> remarkChanged(action.remark)
            is PassbookEntryAction.SaveTransaction -> saveTransaction()
            is PassbookEntryAction.UpdateSelectedCategoryAndPaymentMode -> updateSelectedCategoryAndPaymentMode(action.categoryId,action.paymentId)
            is PassbookEntryAction.OnPaymentModeChange -> onPaymentModeChange()
            is PassbookEntryAction.OnBackPress -> onBackPress()
        }
    }

    private fun onBackPress() {
        viewModelScope.launch {
            _passbookEvent.emit(
                PassbookEntryEvent.OnBackPress
            )
        }
    }

    private fun onPaymentModeChange() {
        viewModelScope.launch {
            _passbookEvent.emit(
                PassbookEntryEvent.OnPaymentModeChange(
                    paymentModeId = _passbookEntryState.value.paymentMode?.id
                )
            )
        }
    }

    private fun onCategoryChange() {
        viewModelScope.launch {
            _passbookEvent.emit(
                PassbookEntryEvent.OnCategoryChange(
                    transactionType = _passbookEntryState.value.entryType,
                    categoryId = _passbookEntryState.value.category?.id
                )
            )
        }
    }

    private fun amountChanged(amount: String) {
        // If empty, set to empty and return
        if (amount.isEmpty()) {
            _passbookEntryState.update { it.copy(amount = amount) }
            return
        }

        // Check if the input is a valid number format
        val isValidNumberFormat = amount.matches(Regex("^-?\\d*\\.?\\d*$"))
        if (!isValidNumberFormat) return

        // Try parsing to Double to check range
        try {
            amount.toDouble() // Just to validate if it's in Double range
            _passbookEntryState.update { it.copy(amount = amount) }
        } catch (e: NumberFormatException) {
            // If number is too large/small for Double, keep previous value
            return
        }
    }

    private fun updateSelectedCategoryAndPaymentMode(categoryId:String?,paymentModeId:String?) {
        viewModelScope.launch {
            launch {
                if (categoryId.isNullOrEmpty())return@launch
                when (val result = getCategories.invoke(categoryId)) {
                    is Result.Success -> {
                        _passbookEntryState.update {
                            it.copy(
                                category = result.data
                            )
                        }
                    }

                    is Result.Error -> {
                        Timber.d("Error: ${result.error.asUiText().asString(context)}")
                    }
                }
            }
            launch {
                if (paymentModeId.isNullOrEmpty())return@launch
                when (val result = getPayment.invoke(paymentModeId)) {
                    is Result.Success -> {
                        _passbookEntryState.update {
                            it.copy(
                                paymentMode = result.data
                            )
                        }
                    }

                    is Result.Error -> {
                        Timber.d("Error: ${result.error.asUiText().asString(context)}")
                    }
                }
            }
        }
    }

    private fun dateChanged(date: String) {
        _passbookEntryState.update {
            it.copy(date = date)
        }
    }

    private fun paymentModeChanged(paymentMode: PaymentMode) {
        _passbookEntryState.update {
            it.copy(paymentMode = paymentMode)
        }
    }

    private fun remarkChanged(remark: String) {
        _passbookEntryState.update {
            it.copy(remark = remark)
        }
    }

    private fun saveTransaction() {
        if (_passbookEntryState.value.entryType == TransactionType.Income) {
            saveIncome()
        } else {
            saveExpense()
        }
    }

    private fun saveIncome() {
        _passbookEntryState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val income = SaveTransaction(
                amount = _passbookEntryState.value.amount.toDouble(),
                remark = _passbookEntryState.value.remark,
                category = _passbookEntryState.value.category,
                paymentMode = _passbookEntryState.value.paymentMode,
                type = _passbookEntryState.value.entryType
            )
            when (val result = saveIncome.invoke(income)) {
                is Result.Error -> {
                    _passbookEntryState.update {
                        it.copy(isLoading = false)
                    }
                    Timber.d("Error: ${result.error.asUiText().asString(context)}")
                }

                is Result.Success -> {
                    _passbookEntryState.update {
                        it.copy(isLoading = false)
                    }
                    _passbookEvent.emit(PassbookEntryEvent.TransactionSaved)
                }
            }
        }

    }

    private fun saveExpense() {
        viewModelScope.launch {
            val expense = SaveTransaction(
                amount = _passbookEntryState.value.amount.toDouble(),
                remark = _passbookEntryState.value.remark,
                category = _passbookEntryState.value.category,
                paymentMode = _passbookEntryState.value.paymentMode,
                type = _passbookEntryState.value.entryType
            )
            when (val result = saveExpense.invoke(expense)) {
                is Result.Error -> {
                    Timber.d("Error: ${result.error.asUiText().asString(context)}")
                }

                is Result.Success -> _passbookEvent.emit(PassbookEntryEvent.TransactionSaved)
            }
        }
    }
}