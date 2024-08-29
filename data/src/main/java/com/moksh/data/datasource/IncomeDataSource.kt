package com.moksh.data.datasource

import com.moksh.data.dto.IncomeDao
import com.moksh.data.entities.local.IncomeEntity
import com.moksh.data.entities.local.IncomeWithCategory
import com.moksh.domain.util.DataError
import com.moksh.domain.util.EmptyResult
import com.moksh.domain.util.Result
import com.moksh.domain.util.asEmptyDataResult
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import java.util.Timer

class IncomeDataSource(private val incomeDao: IncomeDao) {
    suspend fun insertIncome(income: IncomeEntity): EmptyResult<DataError> {
        return try {
            Result.Success(incomeDao.insertIncome(income)).asEmptyDataResult()
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN).asEmptyDataResult()
        }
    }

    fun getAllIncome(): Result<Flow<List<IncomeWithCategory>>, DataError> {
        return try {
            Result.Success(incomeDao.getIncome())
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    suspend fun updateIncome(income: IncomeEntity): EmptyResult<DataError> {
        return try {
            incomeDao.updateIncome(income)
            Result.Success(Unit).asEmptyDataResult()
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN).asEmptyDataResult()
        }
    }

    suspend fun deleteIncome(id: Long): EmptyResult<DataError> {
        return try {
            incomeDao.deleteIncome(id)
            Result.Success(Unit).asEmptyDataResult()
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN).asEmptyDataResult()
        }
    }
}