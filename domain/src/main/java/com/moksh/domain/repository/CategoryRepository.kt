package com.moksh.domain.repository

import com.moksh.domain.model.request.SaveCategoryRequest
import com.moksh.domain.model.response.Category
import com.moksh.domain.model.response.TransactionType
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface CategoryRepository {
    suspend fun insertCategory(category: SaveCategoryRequest): Result<String, DataError>

    fun getAllCategories(): Result<Flow<List<Category>>, DataError>

    fun getCategoriesByType(type: TransactionType): Result<Flow<List<Category>>, DataError>

    suspend fun getCategoryById(id: String): Result<Category, DataError>

    suspend fun updateCategory(category: Category): Result<Unit, DataError>

    suspend fun deleteCategory(category: Category): Result<Unit, DataError>

    suspend fun deleteCategoryById(id: String): Result<Unit, DataError>

    fun searchCategories(query: String,type: TransactionType): Result<Flow<List<Category>>, DataError>

    suspend fun getCategoryCount(): Result<Int, DataError>

    suspend fun getUnsyncedCategories(): Result<List<Category>, DataError>

    suspend fun markCategoriesAsSynced(ids: List<String>): Result<Unit, DataError>

    suspend fun getCategoriesUpdatedAfter(lastSyncTime: Date): Result<List<Category>, DataError>
    suspend fun insertDefaultCategories(): Result<Unit, DataError>
}
