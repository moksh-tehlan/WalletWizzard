package com.moksh.domain.repository

import com.moksh.domain.model.request.InsertSaving
import com.moksh.domain.model.response.Savings
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface SavingRepository {
    // Basic CRUD operations
    fun getAllActiveSavings(): Result<Flow<List<Savings>>,DataError>

    suspend fun getSavingById(id: String): Result<Savings?, DataError>

    suspend fun insertSaving(saving: InsertSaving): Result<String, DataError>

    suspend fun updateSaving(saving: Savings): Result<Unit, DataError>

    suspend fun deleteSaving(savingId: String): Result<Unit, DataError>

    // Progress tracking
    suspend fun getTotalSavings(): Result<Double?, DataError>

    suspend fun getExpiredSavings(): Result<List<Savings>, DataError>

    suspend fun getSavingsByPriority(): Result<List<Savings>, DataError>

    // Sync operations
    suspend fun getUnsyncedSavings(): Result<List<Savings>, DataError>

    suspend fun markSavingsAsSynced(ids: List<String>): Result<Unit, DataError>

    suspend fun getSavingsUpdatedAfter(lastSyncTime: Date): Result<List<Savings>, DataError>

    // Search operations
    fun searchSavings(query: String): Result<Flow<List<Savings>>,DataError>

    // Analytics operations
    suspend fun getTopSavingsByAmount(limit: Int): Result<List<Savings>, DataError>

    suspend fun getCompletedSavings(): Result<List<Savings>, DataError>

    suspend fun getActiveSavingsCount(): Result<Int, DataError>

    suspend fun getExpiredUncompletedSavingsCount(): Result<Int, DataError>
}