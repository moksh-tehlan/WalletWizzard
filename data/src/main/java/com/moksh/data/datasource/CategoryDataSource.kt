package com.moksh.data.datasource

import com.moksh.data.dao.CategoryDao
import com.moksh.data.entities.local.CategoryEntity
import com.moksh.data.entities.utils.safeCall
import com.moksh.domain.model.response.TransactionType
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.Date

class CategoryDataSource(private val categoryDao: CategoryDao) {

    fun getAllCategories(): Result<Flow<List<CategoryEntity>>, DataError> = safeCall {
        categoryDao.getAllCategories()
    }

    fun getCategoriesByType(type: TransactionType): Result<Flow<List<CategoryEntity>>, DataError> =
        safeCall {
            val categoryList = categoryDao.getCategoriesByType(type)
            categoryList
        }

    suspend fun insertCategory(category: CategoryEntity): Result<String, DataError> =
        safeCall {
            categoryDao.insertCategory(category)
            category.id
        }

    suspend fun getCategoryById(id: String): Result<CategoryEntity, DataError> = safeCall {
        categoryDao.getCategoryById(id)
    }

    suspend fun updateCategory(category: CategoryEntity): Result<Unit, DataError> =
        safeCall {
            categoryDao.updateCategory(category)
        }

    suspend fun deleteCategory(category: CategoryEntity): Result<Unit, DataError> =
        safeCall {
            categoryDao.deleteCategory(category)
        }

    fun searchCategories(
        query: String,
        type: TransactionType
    ): Result<Flow<List<CategoryEntity>>, DataError> =
        safeCall {
            categoryDao.searchCategories(query, type)
        }

    suspend fun getCategoryCount(): Result<Int, DataError> = safeCall {
        categoryDao.getCategoryCount()
    }

    suspend fun getUnsyncedCategories(): Result<List<CategoryEntity>, DataError> =
        safeCall {
            categoryDao.getUnsyncedCategories()
        }

    suspend fun markCategoriesAsSynced(ids: List<String>): Result<Unit, DataError> =
        safeCall {
            categoryDao.markCategoriesAsSynced(ids)
        }

    suspend fun getCategoriesUpdatedAfter(lastSyncTime: Date): Result<List<CategoryEntity>, DataError> =
        safeCall {
            categoryDao.getCategoriesUpdatedAfter(lastSyncTime)
        }
}