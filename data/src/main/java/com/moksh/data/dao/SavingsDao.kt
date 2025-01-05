package com.moksh.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.moksh.data.entities.local.SavingsEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface SavingDao {
    @Query("SELECT * FROM savings WHERE is_active = 1 ORDER BY created_at DESC")
    fun getAllActiveSavings(): Flow<List<SavingsEntity>>

    @Query("SELECT * FROM savings WHERE id = :savingId")
    suspend fun getSavingById(savingId: String): SavingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSaving(saving: SavingsEntity)

    @Update
    suspend fun updateSaving(saving: SavingsEntity)

    @Query("UPDATE savings SET is_active = 0 WHERE id = :savingId")
    suspend fun softDeleteSaving(savingId: String)

    @Query("SELECT * FROM savings WHERE end_date IS NOT NULL AND end_date < :currentDate AND is_active = 1")
    suspend fun getExpiredSavings(currentDate: Date): List<SavingsEntity>

    @Query("SELECT SUM(current_amount) FROM savings WHERE is_active = 1")
    suspend fun getTotalSavings(): Double?

    @Query("""
        SELECT * FROM savings 
        WHERE is_active = 1 
        AND end_date IS NOT NULL 
        ORDER BY 
            CASE 
                WHEN current_amount >= target_amount THEN 2
                WHEN end_date < :currentDate THEN 1
                ELSE 0
            END,
            end_date ASC
    """)
    suspend fun getSavingsByPriority(currentDate: Date): List<SavingsEntity>

    // Sync related queries
    @Query("SELECT * FROM savings WHERE is_synced = 0 AND is_active = 1")
    suspend fun getUnsyncedSavings(): List<SavingsEntity>

    @Query("UPDATE savings SET is_synced = 1 WHERE id IN (:ids)")
    suspend fun markSavingsAsSynced(ids: List<String>)

    @Query("SELECT * FROM savings WHERE updated_at > :lastSyncTime AND is_active = 1")
    suspend fun getSavingsUpdatedAfter(lastSyncTime: Date): List<SavingsEntity>

    // Search functionality
    @Query("SELECT * FROM savings WHERE name LIKE '%' || :searchQuery || '%' AND is_active = 1 ORDER BY created_at DESC")
    fun searchSavings(searchQuery: String): Flow<List<SavingsEntity>>

    // Analytics queries
    @Query("SELECT * FROM savings WHERE is_active = 1 ORDER BY current_amount DESC LIMIT :limit")
    suspend fun getTopSavingsByAmount(limit: Int): List<SavingsEntity>

    @Query("SELECT * FROM savings WHERE is_active = 1 AND current_amount >= target_amount")
    suspend fun getCompletedSavings(): List<SavingsEntity>

    @Query("SELECT COUNT(*) FROM savings WHERE is_active = 1")
    suspend fun getActiveSavingsCount(): Int

    @Query("""
        SELECT COUNT(*) FROM savings 
        WHERE is_active = 1 
        AND end_date IS NOT NULL 
        AND end_date < :currentDate 
        AND current_amount < target_amount
    """)
    suspend fun getExpiredUncompletedSavingsCount(currentDate: Date): Int
}