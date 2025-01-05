package com.moksh.data.repository

import com.moksh.data.datasource.SavingsDataSource
import com.moksh.data.mappers.toDto
import com.moksh.data.mappers.toEntity
import com.moksh.domain.model.request.InsertSaving
import com.moksh.domain.model.response.Savings
import com.moksh.domain.repository.SavingRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import com.moksh.domain.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class SavingRepositoryImpl @Inject constructor(
    private val savingsDataSource: SavingsDataSource
) : SavingRepository {

    override fun getAllActiveSavings(): Result<Flow<List<Savings>>, DataError> {
        return savingsDataSource.getAllActiveSavings().map { result ->
            result.map { flow ->
                flow.map { it.toDto() }
            }
        }
    }

    override suspend fun getSavingById(id: String): Result<Savings?, DataError> {
        return savingsDataSource.getSavingById(id).map { it?.toDto() }
    }

    override suspend fun insertSaving(saving: InsertSaving): Result<String, DataError> {
        return savingsDataSource.insertSaving(saving.toEntity())
    }

    override suspend fun updateSaving(saving: Savings): Result<Unit, DataError> {
        return savingsDataSource.updateSaving(saving.toEntity())
    }

    override suspend fun deleteSaving(savingId: String): Result<Unit, DataError> {
        return savingsDataSource.deleteSaving(savingId)
    }

    override suspend fun getTotalSavings(): Result<Double?, DataError> {
        return savingsDataSource.getTotalSavings()
    }

    override suspend fun getExpiredSavings(): Result<List<Savings>, DataError> {
        return savingsDataSource.getExpiredSavings(
            currentDate = Date()
        ).map { it.map { entity -> entity.toDto() } }
    }

    override suspend fun getSavingsByPriority(): Result<List<Savings>, DataError> {
        return savingsDataSource.getSavingsByPriority(
            currentDate = Date()
        ).map { it.map { entity -> entity.toDto() } }
    }

    override suspend fun getUnsyncedSavings(): Result<List<Savings>, DataError> {
        return savingsDataSource.getUnsyncedSavings().map { it.map { entity -> entity.toDto() } }
    }

    override suspend fun markSavingsAsSynced(ids: List<String>): Result<Unit, DataError> {
        return savingsDataSource.markSavingsAsSynced(ids)
    }

    override suspend fun getSavingsUpdatedAfter(lastSyncTime: Date): Result<List<Savings>, DataError> {
        return savingsDataSource.getSavingsUpdatedAfter(lastSyncTime)
            .map { it.map { entity -> entity.toDto() } }
    }

    override fun searchSavings(query: String): Result<Flow<List<Savings>>, DataError> {
        return savingsDataSource.searchSavings(query).map {
            it.map { list ->
                list.map { entity -> entity.toDto() }
            }
        }
    }

    override suspend fun getTopSavingsByAmount(limit: Int): Result<List<Savings>, DataError> {
        return savingsDataSource.getTopSavingsByAmount(limit)
            .map { it.map { entity -> entity.toDto() } }
    }

    override suspend fun getCompletedSavings(): Result<List<Savings>, DataError> {
        return savingsDataSource.getCompletedSavings().map { it.map { entity -> entity.toDto() } }
    }

    override suspend fun getActiveSavingsCount(): Result<Int, DataError> {
        return savingsDataSource.getActiveSavingsCount()
    }

    override suspend fun getExpiredUncompletedSavingsCount(): Result<Int, DataError> {
        return savingsDataSource.getExpiredUncompletedSavingsCount(
            currentDate = Date()
        )
    }
}
