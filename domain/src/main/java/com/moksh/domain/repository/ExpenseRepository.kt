package com.moksh.domain.repository

import com.moksh.domain.model.response.Expense
import com.moksh.domain.util.DataError
import com.moksh.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getExpenses(): Flow<List<Expense>>
    suspend fun saveExpense(expense: Expense): EmptyResult<DataError>
    suspend fun updateExpense(expense: Expense): EmptyResult<DataError>
    suspend fun deleteExpense(id: Long): EmptyResult<DataError>
}