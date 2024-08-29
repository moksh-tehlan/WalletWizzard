package com.moksh.data.datasource

import com.moksh.data.dto.CategoryDao
import com.moksh.data.entities.local.CategoryEntity
import com.moksh.domain.util.DataError
import com.moksh.domain.util.EmptyResult
import com.moksh.domain.util.Result
import com.moksh.domain.util.asEmptyDataResult
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

class CategoryDataSource(private val categoryDao: CategoryDao) {
    suspend fun insertCategory(category: CategoryEntity): Result<Long, DataError> {
        return try {
            Result.Success(categoryDao.insertCategory(category))
        } catch (e: Exception) {
            Timber.d("ErrorCategory: ${e.message}")
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    fun getAllCategory(): Result<Flow<List<CategoryEntity>>, DataError> {
        return try {
            Result.Success(categoryDao.getAllCategories())
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    suspend fun getCategoryById(id: Long): Result<CategoryEntity, DataError> {
        return try {
            Result.Success(categoryDao.getCategoryById(id))
        } catch (e: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}