package com.moksh.data.repository

import com.moksh.data.datasource.CategoryDataSource
import com.moksh.data.datasource.ExpensesDataSource
import com.moksh.data.entities.local.CategoryEntity
import com.moksh.data.entities.local.ExpenseEntity
import com.moksh.data.mappers.toExpense
import com.moksh.domain.model.response.Expense
import com.moksh.domain.repository.ExpenseRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseRepositoryImpl @Inject constructor(
    private val dataSource: ExpensesDataSource,
    private val categoryDataSource: CategoryDataSource
) : ExpenseRepository {
    override fun getExpenses(): Flow<List<Expense>> {
        return dataSource.getAllExpenses().map {
            it.map { expenseEntity ->
                expenseEntity.toExpense()
            }
        }
    }

    override suspend fun saveExpense(expense: Expense): EmptyResult<DataError> {
        return dataSource.insertExpense(ExpenseEntity.fromExpense(expense).copy(categoryId = 1))
    }

    override suspend fun updateExpense(expense: Expense): EmptyResult<DataError> {
        return dataSource.updateExpense(ExpenseEntity.fromExpense(expense))
    }

    override suspend fun deleteExpense(id: Long): EmptyResult<DataError> {
        return dataSource.deleteExpense(id)
    }
}