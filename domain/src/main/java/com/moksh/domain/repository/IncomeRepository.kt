package com.moksh.domain.repository

import com.moksh.domain.model.response.Income
import com.moksh.domain.util.DataError
import com.moksh.domain.util.EmptyResult
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface IncomeRepository {
    fun getIncome(): Result<Flow<List<Income>>, DataError>
    suspend fun saveIncome(income: Income): EmptyResult<DataError>
    suspend fun updateIncome(income: Income): EmptyResult<DataError>
    suspend fun deleteIncome(id: Long): EmptyResult<DataError>
}