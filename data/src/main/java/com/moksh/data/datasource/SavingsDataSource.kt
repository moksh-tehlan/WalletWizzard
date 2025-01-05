package com.moksh.data.datasource

import com.moksh.data.dao.SavingDao
import com.moksh.data.entities.local.SavingsEntity
import com.moksh.data.entities.utils.safeCall
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class SavingsDataSource @Inject constructor(
    private val savingsDao: SavingDao
) {
    fun getAllActiveSavings(): Result<Flow<List<SavingsEntity>>, DataError> = safeCall {
        savingsDao.getAllActiveSavings()
    }

    suspend fun getSavingById(id: String): Result<SavingsEntity?, DataError> = safeCall {
        savingsDao.getSavingById(id)
    }

    suspend fun insertSaving(saving: SavingsEntity): Result<String, DataError> = safeCall {
        savingsDao.insertSaving(saving)
        saving.id
    }

    suspend fun updateSaving(saving: SavingsEntity): Result<Unit, DataError> = safeCall {
        savingsDao.updateSaving(saving)
    }

    suspend fun deleteSaving(savingId: String): Result<Unit, DataError> = safeCall {
        savingsDao.softDeleteSaving(savingId)
    }

    suspend fun getExpiredSavings(currentDate: Date): Result<List<SavingsEntity>, DataError> = safeCall {
        savingsDao.getExpiredSavings(currentDate)
    }

    suspend fun getTotalSavings(): Result<Double?, DataError> = safeCall {
        savingsDao.getTotalSavings()
    }

    suspend fun getSavingsByPriority(currentDate: Date): Result<List<SavingsEntity>, DataError> =
        safeCall {
            savingsDao.getSavingsByPriority(currentDate)
        }

    // Sync related methods
    suspend fun getUnsyncedSavings(): Result<List<SavingsEntity>, DataError> = safeCall {
        savingsDao.getUnsyncedSavings()
    }

    suspend fun markSavingsAsSynced(ids: List<String>): Result<Unit, DataError> = safeCall {
        savingsDao.markSavingsAsSynced(ids)
    }

    suspend fun getSavingsUpdatedAfter(lastSyncTime: Date): Result<List<SavingsEntity>, DataError> =
        safeCall {
            savingsDao.getSavingsUpdatedAfter(lastSyncTime)
        }

    fun searchSavings(query: String): Result<Flow<List<SavingsEntity>>, DataError> = safeCall {
        savingsDao.searchSavings(query)
    }

    suspend fun getTopSavingsByAmount(limit: Int): Result<List<SavingsEntity>, DataError> = safeCall {
        savingsDao.getTopSavingsByAmount(limit)
    }

    suspend fun getCompletedSavings(): Result<List<SavingsEntity>, DataError> = safeCall {
        savingsDao.getCompletedSavings()
    }

    suspend fun getActiveSavingsCount(): Result<Int, DataError> = safeCall {
        savingsDao.getActiveSavingsCount()
    }

    suspend fun getExpiredUncompletedSavingsCount(currentDate: Date): Result<Int, DataError> =
        safeCall {
            savingsDao.getExpiredUncompletedSavingsCount(currentDate)
        }
}