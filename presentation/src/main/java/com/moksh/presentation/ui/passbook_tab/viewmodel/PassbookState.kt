package com.moksh.presentation.ui.passbook_tab.viewmodel

import com.moksh.domain.model.response.Expense
import com.moksh.domain.model.response.Income


data class PassbookState(
    val incomeList: List<Income> = emptyList(),
    val expenseList: List<Expense> = emptyList(),
)
