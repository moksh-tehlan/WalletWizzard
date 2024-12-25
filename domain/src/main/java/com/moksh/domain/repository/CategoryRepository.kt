package com.moksh.domain.repository

import com.moksh.domain.model.request.SaveCategoryRequest
import com.moksh.domain.model.response.Category
import com.moksh.domain.model.response.TransactionType
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface CategoryRepository {
    suspend fun insertCategory(category: SaveCategoryRequest): Result<String, DataError.Local>

    fun getAllCategories(): Result<Flow<List<Category>>, DataError.Local>

    fun getCategoriesByType(type: TransactionType): Result<Flow<List<Category>>, DataError.Local>

    suspend fun getCategoryById(id: String): Result<Category, DataError.Local>

    suspend fun updateCategory(category: Category): Result<Unit, DataError.Local>

    suspend fun deleteCategory(category: Category): Result<Unit, DataError.Local>

    suspend fun deleteCategoryById(id: String): Result<Unit, DataError.Local>

    fun searchCategories(query: String,type: TransactionType): Result<Flow<List<Category>>, DataError.Local>

    suspend fun getCategoryCount(): Result<Int, DataError.Local>

    suspend fun getUnsyncedCategories(): Result<List<Category>, DataError.Local>

    suspend fun markCategoriesAsSynced(ids: List<String>): Result<Unit, DataError.Local>

    suspend fun getCategoriesUpdatedAfter(lastSyncTime: Date): Result<List<Category>, DataError.Local>
    suspend fun insertDefaultCategories(): Result<Unit, DataError.Local>
}
