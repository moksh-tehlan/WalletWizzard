package com.moksh.domain.usecases.expense

import com.moksh.domain.repository.ExpenseRepository
import javax.inject.Inject

class GetExpenses @Inject constructor(private val expenseRepository: ExpenseRepository) {
    fun invoke() = expenseRepository.getExpenses()
}