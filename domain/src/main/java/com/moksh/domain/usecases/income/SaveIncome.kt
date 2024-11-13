package com.moksh.domain.usecases.income

import com.moksh.domain.model.response.SaveTransaction
import com.moksh.domain.model.response.Transaction
import com.moksh.domain.repository.TransactionRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.EmptyResult
import com.moksh.domain.util.asEmptyDataResult

class SaveIncome(private val incomeRepository: TransactionRepository) {
    suspend fun invoke(income: SaveTransaction): EmptyResult<DataError> {
        return incomeRepository.insertTransaction(income).asEmptyDataResult()
    }
}