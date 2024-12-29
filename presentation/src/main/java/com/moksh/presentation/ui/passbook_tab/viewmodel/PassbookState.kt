package com.moksh.presentation.ui.passbook_tab.viewmodel

import com.moksh.domain.model.response.Transaction
import com.moksh.presentation.core.utils.UiText
import java.util.Calendar


data class PassbookState(
    val incomeState: PassBookEntryState = PassBookEntryState(),
    val expenseState: PassBookEntryState = PassBookEntryState(),
)

data class PassBookEntryState(
    val transactionList: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
) {
    val currentMonthSum: Double
        get() {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, 1)
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
            }

            val startOfMonth = calendar.time
            calendar.add(Calendar.MONTH, 1)
            val startOfNextMonth = calendar.time

            return transactionList.filter {
                it.createdAt in startOfMonth..<startOfNextMonth
            }.sumOf { it.amount }
        }
}

