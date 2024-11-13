package com.moksh.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.moksh.data.entities.local.CategoryEntity
import com.moksh.domain.model.response.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE type = :type ORDER BY name ASC")
    fun getCategoriesByType(type: TransactionType): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: String): CategoryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun deleteCategoryById(id: String)

    @Query("SELECT COUNT(*) FROM categories")
    suspend fun getCategoryCount(): Int

    @Query("SELECT * FROM categories WHERE is_synced = 0")
    suspend fun getUnsyncedCategories(): List<CategoryEntity>

    @Query("UPDATE categories SET is_synced = 1 WHERE id IN (:ids)")
    suspend fun markCategoriesAsSynced(ids: List<String>)

    @Query("SELECT * FROM categories WHERE name LIKE '%' || :searchQuery || '%' AND type = :type ORDER BY name ASC")
    fun searchCategories(searchQuery: String,type: TransactionType): Flow<List<CategoryEntity>>

    @Query("SELECT c.* FROM categories c INNER JOIN transactions t ON c.id = t.category_id GROUP BY c.id ORDER BY COUNT(t.id) DESC LIMIT :limit")
    suspend fun getMostUsedCategories(limit: Int): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE updated_at > :lastSyncTime")
    suspend fun getCategoriesUpdatedAfter(lastSyncTime: Date): List<CategoryEntity>
}