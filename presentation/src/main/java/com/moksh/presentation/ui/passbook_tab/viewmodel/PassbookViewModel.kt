package com.moksh.presentation.ui.passbook_tab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moksh.domain.usecases.expense.GetExpenses
import com.moksh.domain.usecases.income.GetIncome
import com.moksh.domain.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PassbookViewModel @Inject constructor(
    private val getIncome: GetIncome,
    private val getExpenses: GetExpenses
) : ViewModel() {
    private val _passbookState = MutableStateFlow(PassbookState())
    val passbookState = _passbookState.asStateFlow()

    init {
        getIncomeList()
        getExpenseList()
    }

    private fun getIncomeList() {
        viewModelScope.launch {
            when (val result = getIncome.invoke()) {
                is Result.Success -> {
                    result.data.collectLatest {
                        _passbookState.value = _passbookState.value.copy(incomeList = it)
                    }
                }

                is Result.Error -> {}
            }
        }
    }

    private fun getExpenseList() {
        viewModelScope.launch {
            when (val result = getExpenses.invoke()) {
                is Result.Success -> {
                    result.data.collectLatest {
                        _passbookState.value = _passbookState.value.copy(expenseList = it)
                    }
                }

                is Result.Error -> {}
            }
        }
    }
}