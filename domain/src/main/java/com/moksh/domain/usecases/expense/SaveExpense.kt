package com.moksh.domain.usecases.expense

import com.moksh.domain.model.response.SaveTransaction
import com.moksh.domain.model.response.Transaction
import com.moksh.domain.repository.TransactionRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.EmptyResult
import com.moksh.domain.util.asEmptyDataResult
import javax.inject.Inject

class SaveExpense @Inject constructor(private val expenseRepository: TransactionRepository) {
    suspend fun invoke(expense: SaveTransaction): EmptyResult<DataError> {
        return expenseRepository.insertTransaction(expense).asEmptyDataResult()
    }
}