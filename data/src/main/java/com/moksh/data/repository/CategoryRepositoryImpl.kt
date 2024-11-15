package com.moksh.data.repository

import com.moksh.data.datasource.CategoryDataSource
import com.moksh.data.entities.local.CategoryEntity
import com.moksh.data.mappers.toDto
import com.moksh.data.mappers.toEntity
import com.moksh.domain.model.response.Category
import com.moksh.domain.model.response.TransactionType
import com.moksh.domain.repository.CategoryRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import com.moksh.domain.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val dataSource: CategoryDataSource) :
    CategoryRepository {
    override fun getAllCategories(): Result<Flow<List<Category>>, DataError.Local> {
        return dataSource.getAllCategories().map { flow ->
            flow.map { categoryList ->
                categoryList.map { categoryEntity ->
                    categoryEntity.toDto()
                }
            }
        }
    }

    override fun getCategoriesByType(type: TransactionType): Result<Flow<List<Category>>, DataError.Local> {
        return dataSource.getCategoriesByType(type).map { flow ->
            flow.map { categoryList ->
                categoryList.map { categoryEntity ->
                    categoryEntity.toDto()
                }
            }
        }
    }

    override suspend fun insertCategory(category: Category): Result<String, DataError.Local> =
        dataSource.insertCategory(category.toEntity())

    override suspend fun getCategoryById(id: String): Result<Category, DataError.Local> =
        dataSource.getCategoryById(id).map { it.toDto() }

    override suspend fun updateCategory(category: Category): Result<Unit, DataError.Local> =
        dataSource.updateCategory(category.toEntity())

    override suspend fun deleteCategory(category: Category): Result<Unit, DataError.Local> =
        dataSource.deleteCategory(category.toEntity())

    override suspend fun deleteCategoryById(id: String): Result<Unit, DataError.Local> =
        dataSource.deleteCategory(
            CategoryEntity(
                id = id,
                name = "",
                icon = null,
                color = "",
                type = TransactionType.Expenses
            )
        )

    override fun searchCategories(query: String,type: TransactionType): Result<Flow<List<Category>>, DataError.Local> =
        dataSource.searchCategories(query,type).map { flow ->
            flow.map { categoryList ->
                categoryList.map { categoryEntity ->
                    categoryEntity.toDto()
                }
            }
        }

    override suspend fun getCategoryCount(): Result<Int, DataError.Local> =
        dataSource.getCategoryCount()

    override suspend fun getUnsyncedCategories(): Result<List<Category>, DataError.Local> =
        dataSource.getUnsyncedCategories().map { categoryList ->
            categoryList.map { categoryEntity ->
                categoryEntity.toDto()
            }
        }

    override suspend fun markCategoriesAsSynced(ids: List<String>): Result<Unit, DataError.Local> =
        dataSource.markCategoriesAsSynced(ids)

    override suspend fun getCategoriesUpdatedAfter(lastSyncTime: Date): Result<List<Category>, DataError.Local> =
        dataSource.getCategoriesUpdatedAfter(lastSyncTime).map { categoryList ->
            categoryList.map { categoryEntity ->
                categoryEntity.toDto()
            }
        }

    override suspend fun insertDefaultCategories(): Result<Unit, DataError.Local> {
        val categoriesList = listOf(
            CategoryEntity(name = "Income", type = TransactionType.Income),
            CategoryEntity(name = "Freelance", type = TransactionType.Income),
            CategoryEntity(name = "Royalty", type = TransactionType.Income),
            CategoryEntity(name = "Rent", type = TransactionType.Expenses),
            CategoryEntity(name = "Gym Fees", type = TransactionType.Expenses),
            CategoryEntity(name = "Library Fees", type = TransactionType.Expenses),
        )
        categoriesList.forEach { category ->
            dataSource.insertCategory(category)
        }
        return Result.Success(Unit)
    }
}