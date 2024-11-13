package com.moksh.domain.usecases.expense

import com.moksh.domain.model.response.Transaction
import com.moksh.domain.model.response.TransactionType
import com.moksh.domain.repository.TransactionRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpenses @Inject constructor(private val expenseRepository: TransactionRepository) {
    fun invoke(): Result<Flow<List<Transaction>>, DataError> {
        return expenseRepository.getTransactionsByTypeWithDetails(type = TransactionType.Expenses)
    }
}