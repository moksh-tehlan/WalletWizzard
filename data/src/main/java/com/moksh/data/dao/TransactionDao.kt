package com.moksh.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.moksh.data.entities.local.TransactionEntity
import com.moksh.data.entities.local.TransactionWithDetails
import com.moksh.domain.model.response.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface TransactionDao {
    @Transaction
    @Query("SELECT * FROM transactions ORDER BY created_at DESC")
    fun getAllTransactionsWithDetails(): Flow<List<TransactionWithDetails>>

    @Transaction
    @Query("SELECT * FROM transactions WHERE type = :type ORDER BY created_at DESC")
    fun getTransactionsByTypeWithDetails(type: TransactionType): Flow<List<TransactionWithDetails>>

    @Transaction
    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: String): TransactionWithDetails

    @Transaction
    @Query("SELECT * FROM transactions WHERE created_at BETWEEN :startDate AND :endDate ORDER BY created_at DESC")
    suspend fun getTransactionsBetweenDates(startDate: Date, endDate: Date): List<TransactionWithDetails>

    @Transaction
    @Query("SELECT * FROM transactions WHERE category_id = :categoryId ORDER BY created_at DESC")
    suspend fun getTransactionsByCategoryWithDetails(categoryId: String): List<TransactionWithDetails>

    @Transaction
    @Query("SELECT * FROM transactions WHERE payment_mode_id = :paymentModeId ORDER BY created_at DESC")
    suspend fun getTransactionsByPaymentModeWithDetails(paymentModeId: String): List<TransactionWithDetails>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: String)

    @Query("SELECT SUM(amount) FROM transactions WHERE type = :type")
    suspend fun getTotalAmountByType(type: TransactionType): Double?

    @Query("SELECT SUM(amount) FROM transactions WHERE type = :type AND created_at BETWEEN :startDate AND :endDate")
    suspend fun getTotalAmountByTypeAndDateRange(type: TransactionType, startDate: Date, endDate: Date): Double?

    @Query("SELECT COUNT(*) FROM transactions")
    suspend fun getTransactionCount(): Int

    @Query("SELECT * FROM transactions WHERE is_synced = 0")
    suspend fun getUnsyncedTransactions(): List<TransactionWithDetails>

    @Query("UPDATE transactions SET is_synced = 1 WHERE id IN (:ids)")
    suspend fun markTransactionsAsSynced(ids: List<String>)

    @Transaction
    @Query("SELECT * FROM transactions WHERE remark LIKE '%' || :searchQuery || '%' ORDER BY created_at DESC")
    suspend fun searchTransactionsWithDetails(searchQuery: String): List<TransactionWithDetails>
}