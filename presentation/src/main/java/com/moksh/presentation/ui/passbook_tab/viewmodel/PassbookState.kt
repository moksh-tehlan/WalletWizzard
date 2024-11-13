package com.moksh.presentation.ui.passbook_tab.viewmodel

import com.moksh.domain.model.response.Transaction


data class PassbookState(
    val incomeList: List<Transaction> = emptyList(),
    val expenseList: List<Transaction> = emptyList(),
)
