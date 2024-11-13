package com.moksh.domain.usecases.income

import com.moksh.domain.model.response.Transaction
import com.moksh.domain.model.response.TransactionType
import com.moksh.domain.repository.TransactionRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow

class GetIncome(private val incomeRepository: TransactionRepository) {
    fun invoke(): Result<Flow<List<Transaction>>, DataError> {
        return incomeRepository.getTransactionsByTypeWithDetails(type = TransactionType.Income)
    }
}