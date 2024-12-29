package com.moksh.presentation.ui.passbook_tab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moksh.domain.usecases.expense.GetExpenses
import com.moksh.domain.usecases.income.GetIncome
import com.moksh.domain.util.Result
import com.moksh.presentation.core.utils.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PassbookViewModel @Inject constructor(
    private val getIncome: GetIncome,
    private val getExpenses: GetExpenses
) : ViewModel() {
    private val _passbookState = MutableStateFlow(PassbookState())
    val passbookState = _passbookState
        .onStart {
            getIncomeList()
            getExpenseList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            PassbookState()
        )

    private fun getIncomeList() {
        val incomeState = PassBookEntryState(
            isLoading = true,
            error = null
        )
        _passbookState.value = _passbookState.value.copy(expenseState = incomeState)
        viewModelScope.launch {
            when (val result = getIncome.invoke()) {
                is Result.Success -> {
                    result.data.collectLatest { transactionList ->
                        val resultIncomeState = incomeState.copy(
                            transactionList = transactionList,
                            isLoading = false,
                            error = null
                        )
                        _passbookState.value =
                            _passbookState.value.copy(incomeState = resultIncomeState)
                    }
                }

                is Result.Error -> {
                    val resultIncomeState = incomeState.copy(
                        isLoading = false,
                        error = result.error.asUiText()
                    )
                    _passbookState.value =
                        _passbookState.value.copy(incomeState = resultIncomeState)
                }
            }
        }
    }

    private fun getExpenseList() {
        val expensesState = PassBookEntryState(
            isLoading = true,
            error = null
        )
        _passbookState.value = _passbookState.value.copy(expenseState = expensesState)
        viewModelScope.launch {
            when (val result = getExpenses.invoke()) {
                is Result.Success -> {
                    result.data.collectLatest { transactionList ->
                        val resultIncomeState = expensesState.copy(
                            transactionList = transactionList,
                            isLoading = false,
                            error = null
                        )
                        _passbookState.value =
                            _passbookState.value.copy(expenseState = resultIncomeState)
                    }
                }

                is Result.Error -> {
                    val resultIncomeState = expensesState.copy(
                        isLoading = false,
                        error = result.error.asUiText()
                    )
                    _passbookState.value =
                        _passbookState.value.copy(expenseState = resultIncomeState)
                }
            }
        }
    }
}