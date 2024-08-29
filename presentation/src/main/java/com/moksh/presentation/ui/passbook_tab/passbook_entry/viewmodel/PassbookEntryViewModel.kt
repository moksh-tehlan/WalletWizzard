package com.moksh.presentation.ui.passbook_tab.passbook_entry.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moksh.domain.model.response.Category
import com.moksh.domain.model.response.Expense
import com.moksh.domain.model.response.Income
import com.moksh.domain.model.response.PaymentMode
import com.moksh.domain.usecases.expense.SaveExpense
import com.moksh.domain.usecases.income.SaveIncome
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import com.moksh.presentation.ui.passbook_tab.passbook_entry.component.EntryType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PassbookEntryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val saveIncome: SaveIncome,
    private val saveExpense: SaveExpense
) : ViewModel() {

    private val _passbookEntryState = MutableStateFlow(PassbookEntryState())
    val passbookEntryState = _passbookEntryState.asStateFlow()

    private val _passbookEvent = MutableSharedFlow<PassbookEntryEvent>()
    val passbookEvent = _passbookEvent.asSharedFlow()

    init {
        val entryType = EntryType.valueOf(savedStateHandle.get<String>("entryType"))
        _passbookEntryState.value = _passbookEntryState.value.copy(
            entryType = entryType
        )
    }

    fun onAction(action: PassbookEntryAction) {
        when (action) {
            is PassbookEntryAction.AmountChanged -> amountChanged(action.amount)
            is PassbookEntryAction.CategoryChanged -> categoryChanged(action.category)
            is PassbookEntryAction.DateChanged -> dateChanged(action.date)
            is PassbookEntryAction.PaymentModeChanged -> paymentModeChanged(action.paymentMode)
            is PassbookEntryAction.RemarkChanged -> remarkChanged(action.remark)
            is PassbookEntryAction.SaveTransaction -> saveTransaction()
        }
    }

    private fun amountChanged(amount: String) {
        _passbookEntryState.update {
            it.copy(amount = amount.toDouble())
        }
    }

    private fun categoryChanged(category: Category) {
        _passbookEntryState.update {
            it.copy(category = category)
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
        if (_passbookEntryState.value.entryType == EntryType.Income) {
            saveIncome()
        } else {
            saveExpense()
        }
    }

    private fun saveIncome() {
        viewModelScope.launch {
            val income = Income(
                amount = _passbookEntryState.value.amount,
                remark = _passbookEntryState.value.remark,
                category = _passbookEntryState.value.category,
                paymentMode = _passbookEntryState.value.paymentMode,
                date = _passbookEntryState.value.date
            )
            when (val result = saveIncome.invoke(income)) {
                is Result.Error -> {
                    when (result.error) {
                        DataError.Local.DISK_FULL -> TODO()
                        DataError.Local.UNKNOWN -> TODO()
                        DataError.Local.DUPLICATE_DATA -> TODO()
                        DataError.Local.DATABASE_FULL -> TODO()
                        DataError.Local.SQL_ERROR -> TODO()
                        else -> {}
                    }
                }

                is Result.Success -> _passbookEvent.emit(PassbookEntryEvent.TransactionSaved)
            }
        }

    }

    private fun saveExpense() {
        viewModelScope.launch(Dispatchers.IO) {
            val expense = Expense(
                amount = _passbookEntryState.value.amount,
                remark = _passbookEntryState.value.remark,
                category = _passbookEntryState.value.category,
                paymentMode = _passbookEntryState.value.paymentMode,
                date = _passbookEntryState.value.date
            )
            when (val result = saveExpense.invoke(expense)) {
                is Result.Error -> {
                    when (result.error) {
//                        DataError.Local.DISK_FULL -> TODO()
//                        DataError.Local.UNKNOWN -> TODO()
//                        DataError.Local.DUPLICATE_DATA -> TODO()
//                        DataError.Local.DATABASE_FULL -> TODO()
//                        DataError.Local.SQL_ERROR -> TODO()
                        else -> {}
                    }
                }

                is Result.Success -> _passbookEvent.emit(PassbookEntryEvent.TransactionSaved)
            }
        }
    }
}