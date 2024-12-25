package com.moksh.data.datasource

import com.moksh.data.dao.TransactionDao
import com.moksh.data.entities.local.TransactionEntity
import com.moksh.data.entities.local.TransactionWithDetails
import com.moksh.data.entities.utils.safeCall
import com.moksh.domain.model.response.TransactionType
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.Date

class TransactionDataSource(private val transactionDao: TransactionDao) {
    suspend fun insertTransaction(transaction: TransactionEntity): Result<String, DataError> =
        safeCall {
            transactionDao.insertTransaction(transaction)
            transaction.id
        }

    fun getAllTransactionsWithDetails(): Result<Flow<List<TransactionWithDetails>>, DataError> {
        return safeCall {
            transactionDao.getAllTransactionsWithDetails()
        }
    }

    fun getTransactionsByTypeWithDetails(type: TransactionType): Result<Flow<List<TransactionWithDetails>>, DataError> {
        return safeCall {
            transactionDao.getTransactionsByTypeWithDetails(type)
        }
    }

    suspend fun getTransactionById(id: String): Result<TransactionWithDetails, DataError> =
        safeCall {
            transactionDao.getTransactionById(id)
        }

    suspend fun updateTransaction(transaction: TransactionEntity): Result<Unit, DataError> =
        safeCall {
            transactionDao.updateTransaction(transaction)
        }

    suspend fun deleteTransaction(transaction: TransactionEntity): Result<Unit, DataError> =
        safeCall {
            transactionDao.deleteTransaction(transaction)
        }

    suspend fun deleteTransactionById(id: String): Result<Unit, DataError> =
        safeCall {
            transactionDao.deleteTransactionById(id)
        }

    suspend fun getTransactionsBetweenDates(
        startDate: Date,
        endDate: Date
    ): Result<List<TransactionWithDetails>, DataError> = safeCall {
        transactionDao.getTransactionsBetweenDates(startDate, endDate)
    }

    suspend fun getTransactionsByCategoryWithDetails(categoryId: String): Result<List<TransactionWithDetails>, DataError> =
        safeCall {
            transactionDao.getTransactionsByCategoryWithDetails(categoryId)
        }

    suspend fun getTransactionsByPaymentModeWithDetails(paymentModeId: String): Result<List<TransactionWithDetails>, DataError> =
        safeCall {
            transactionDao.getTransactionsByPaymentModeWithDetails(paymentModeId)
        }

    suspend fun getTotalAmountByType(type: TransactionType): Result<Double?, DataError> =
        safeCall {
            transactionDao.getTotalAmountByType(type)
        }

    suspend fun getTotalAmountByTypeAndDateRange(
        type: TransactionType,
        startDate: Date,
        endDate: Date
    ): Result<Double?, DataError> = safeCall {
        transactionDao.getTotalAmountByTypeAndDateRange(type, startDate, endDate)
    }

    suspend fun getTransactionCount(): Result<Int, DataError> =
        safeCall {
            transactionDao.getTransactionCount()
        }

    suspend fun getUnsyncedTransactions(): Result<List<TransactionWithDetails>, DataError> =
        safeCall {
            transactionDao.getUnsyncedTransactions()
        }

    suspend fun markTransactionsAsSynced(ids: List<String>): Result<Unit, DataError> =
        safeCall {
            transactionDao.markTransactionsAsSynced(ids)
        }

    suspend fun searchTransactionsWithDetails(query: String): Result<List<TransactionWithDetails>, DataError> =
        safeCall {
            transactionDao.searchTransactionsWithDetails(query)
        }
}