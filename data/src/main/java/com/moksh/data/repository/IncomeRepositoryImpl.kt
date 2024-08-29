package com.moksh.data.repository

import com.moksh.data.datasource.CategoryDataSource
import com.moksh.data.datasource.IncomeDataSource
import com.moksh.data.entities.local.CategoryEntity
import com.moksh.data.entities.local.IncomeEntity
import com.moksh.data.mappers.toIncome
import com.moksh.domain.model.response.Income
import com.moksh.domain.repository.IncomeRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.EmptyResult
import com.moksh.domain.util.Result
import com.moksh.domain.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IncomeRepositoryImpl @Inject constructor(
    private val dataSource: IncomeDataSource,
    private val categoryDataSource: CategoryDataSource
) :
    IncomeRepository {
    override fun getIncome(): Result<Flow<List<Income>>, DataError> {
        return dataSource.getAllIncome().map { flow ->
            flow.map { incomeList ->
                incomeList.map { income ->
                    income.toIncome()
                }
            }
        }
    }

    override suspend fun saveIncome(income: Income): EmptyResult<DataError> {
        categoryDataSource.insertCategory(CategoryEntity(name = income.category.name))
        return dataSource.insertIncome(IncomeEntity.fromIncome(income).copy(categoryId = 1))
    }

    override suspend fun updateIncome(income: Income): EmptyResult<DataError> {
        return dataSource.updateIncome(IncomeEntity.fromIncome(income))
    }

    override suspend fun deleteIncome(id: Long): EmptyResult<DataError> {
        return dataSource.deleteIncome(id)
    }

}