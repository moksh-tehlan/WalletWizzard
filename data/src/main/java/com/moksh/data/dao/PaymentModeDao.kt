package com.moksh.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.moksh.data.entities.local.PaymentModeEntity
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface PaymentModeDao {
    @Query("SELECT * FROM payment_modes ORDER BY name ASC")
    fun getAllPaymentModes(): Flow<List<PaymentModeEntity>>

    @Query("SELECT * FROM payment_modes WHERE id = :id")
    suspend fun getPaymentModeById(id: String): PaymentModeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaymentMode(paymentMode: PaymentModeEntity)

    @Update
    suspend fun updatePaymentMode(paymentMode: PaymentModeEntity)

    @Delete
    suspend fun deletePaymentMode(paymentMode: PaymentModeEntity)

    @Query("DELETE FROM payment_modes WHERE id = :id")
    suspend fun deletePaymentModeById(id: String)

    @Query("SELECT COUNT(*) FROM payment_modes")
    suspend fun getPaymentModeCount(): Int

    @Query("SELECT * FROM payment_modes WHERE is_synced = 0")
    suspend fun getUnsyncedPaymentModes(): List<PaymentModeEntity>

    @Query("UPDATE payment_modes SET is_synced = 1 WHERE id IN (:ids)")
    suspend fun markPaymentModesAsSynced(ids: List<String>)

    @Query("SELECT * FROM payment_modes WHERE name LIKE '%' || :searchQuery || '%' ORDER BY name ASC")
    suspend fun searchPaymentModes(searchQuery: String): List<PaymentModeEntity>

    @Query("SELECT pm.* FROM payment_modes pm INNER JOIN transactions t ON pm.id = t.payment_mode_id GROUP BY pm.id ORDER BY COUNT(t.id) DESC LIMIT :limit")
    suspend fun getMostUsedPaymentModes(limit: Int): List<PaymentModeEntity>

    @Query("SELECT * FROM payment_modes WHERE updated_at > :lastSyncTime")
    suspend fun getPaymentModesUpdatedAfter(lastSyncTime: Date): List<PaymentModeEntity>
}