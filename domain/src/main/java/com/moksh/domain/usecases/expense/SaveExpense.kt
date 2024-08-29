package com.moksh.domain.usecases.expense

import com.moksh.domain.model.response.Expense
import com.moksh.domain.repository.ExpenseRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.EmptyResult
import javax.inject.Inject

class SaveExpense @Inject constructor(private val expenseRepository: ExpenseRepository) {
    suspend fun invoke(expense: Expense): EmptyResult<DataError> {
        return expenseRepository.saveExpense(expense)
    }
}