package com.moksh.data.datasource

import com.moksh.data.dto.ExpenseDao
import com.moksh.data.entities.local.ExpenseEntity
import com.moksh.data.entities.utils.safeDbCall
import com.moksh.domain.util.DataError
import com.moksh.domain.util.EmptyResult
import com.moksh.domain.util.Result
import com.moksh.domain.util.asEmptyDataResult
import javax.inject.Inject

class ExpensesDataSource @Inject constructor(private val expenseDao: ExpenseDao) {
    fun getAllExpenses() = expenseDao.getExpense()
    suspend fun insertExpense(expense: ExpenseEntity): EmptyResult<DataError> {
        return safeDbCall { expenseDao.insertExpense(expense) }.asEmptyDataResult()
    }

    suspend fun updateExpense(expense: ExpenseEntity): EmptyResult<DataError> {
        return safeDbCall { expenseDao.updateExpense(expense) }.asEmptyDataResult()
    }

    suspend fun deleteExpense(id: Long): EmptyResult<DataError> {
        return safeDbCall { expenseDao.deleteExpense(id) }.asEmptyDataResult()
    }
}