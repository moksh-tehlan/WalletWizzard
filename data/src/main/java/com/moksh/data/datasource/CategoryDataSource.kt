package com.moksh.data.datasource

import com.moksh.data.dao.CategoryDao
import com.moksh.data.entities.local.CategoryEntity
import com.moksh.data.entities.utils.safeDbCall
import com.moksh.domain.model.response.TransactionType
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.Date

class CategoryDataSource(private val categoryDao: CategoryDao) {

    fun getAllCategories(): Result<Flow<List<CategoryEntity>>, DataError.Local> = safeDbCall {
        categoryDao.getAllCategories()
    }

    fun getCategoriesByType(type: TransactionType): Result<Flow<List<CategoryEntity>>, DataError.Local> =
        safeDbCall {
            val categoryList = categoryDao.getCategoriesByType(type)
            categoryList
        }

    suspend fun insertCategory(category: CategoryEntity): Result<String, DataError.Local> =
        safeDbCall {
            categoryDao.insertCategory(category)
            category.id
        }

    suspend fun getCategoryById(id: String): Result<CategoryEntity, DataError.Local> = safeDbCall {
        categoryDao.getCategoryById(id)
    }

    suspend fun updateCategory(category: CategoryEntity): Result<Unit, DataError.Local> =
        safeDbCall {
            categoryDao.updateCategory(category)
        }

    suspend fun deleteCategory(category: CategoryEntity): Result<Unit, DataError.Local> =
        safeDbCall {
            categoryDao.deleteCategory(category)
        }

    fun searchCategories(query: String,type: TransactionType): Result<Flow<List<CategoryEntity>>, DataError.Local> =
        safeDbCall {
            categoryDao.searchCategories(query,type)
        }

    suspend fun getCategoryCount(): Result<Int, DataError.Local> = safeDbCall {
        categoryDao.getCategoryCount()
    }

    suspend fun getUnsyncedCategories(): Result<List<CategoryEntity>, DataError.Local> =
        safeDbCall {
            categoryDao.getUnsyncedCategories()
        }

    suspend fun markCategoriesAsSynced(ids: List<String>): Result<Unit, DataError.Local> =
        safeDbCall {
            categoryDao.markCategoriesAsSynced(ids)
        }

    suspend fun getCategoriesUpdatedAfter(lastSyncTime: Date): Result<List<CategoryEntity>, DataError.Local> =
        safeDbCall {
            categoryDao.getCategoriesUpdatedAfter(lastSyncTime)
        }
}