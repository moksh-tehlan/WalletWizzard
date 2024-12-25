package com.moksh.data.repository

import com.moksh.data.datasource.TransactionDataSource
import com.moksh.data.mappers.toDto
import com.moksh.data.mappers.toEntity
import com.moksh.domain.model.response.SaveTransaction
import com.moksh.domain.model.response.Transaction
import com.moksh.domain.model.response.TransactionType
import com.moksh.domain.repository.TransactionRepository
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import com.moksh.domain.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val localDataSource: TransactionDataSource,
) : TransactionRepository {
    override suspend fun insertTransaction(transaction: SaveTransaction): Result<String, DataError> {
        return localDataSource.insertTransaction(transaction.toEntity())
    }

    override fun getAllTransactionsWithDetails(): Result<Flow<List<Transaction>>, DataError> {
        return localDataSource.getAllTransactionsWithDetails()
            .map { flow ->
                flow.map { item ->
                    item.map { it.toDto() }
                }
            }
    }

    override fun getTransactionsByTypeWithDetails(type: TransactionType): Result<Flow<List<Transaction>>, DataError> {
        return localDataSource.getTransactionsByTypeWithDetails(type)
            .map { flow ->
                flow.map { item ->
                    item.map { it.toDto() }
                }
            }
    }

    override suspend fun getTransactionById(id: String): Result<Transaction, DataError> {
        return localDataSource.getTransactionById(id).map { it.toDto() }
    }

    override suspend fun updateTransaction(transaction: Transaction): Result<Unit, DataError> {
        return localDataSource.updateTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction): Result<Unit, DataError> {
        return localDataSource.deleteTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransactionById(id: String): Result<Unit, DataError> {
        return localDataSource.deleteTransactionById(id)
    }

    override suspend fun getTransactionsBetweenDates(
        startDate: Date,
        endDate: Date
    ): Result<List<Transaction>, DataError> {
        return localDataSource.getTransactionsBetweenDates(startDate, endDate)
            .map { list -> list.map { it.toDto() } }
    }

    override suspend fun getTotalAmountByType(type: TransactionType): Result<Double, DataError> {
        return localDataSource.getTotalAmountByType(type).map { it ?: 0.0 }
    }

    override suspend fun getTotalAmountByTypeAndDateRange(
        type: TransactionType,
        startDate: Date,
        endDate: Date
    ): Result<Double, DataError> {
        return localDataSource.getTotalAmountByTypeAndDateRange(type, startDate, endDate)
            .map { it ?: 0.0 }
    }

    override suspend fun getTransactionCount(): Result<Int, DataError> {
        return localDataSource.getTransactionCount()
    }

    override suspend fun getUnsyncedTransactions(): Result<List<Transaction>, DataError> {
        return localDataSource.getUnsyncedTransactions()
            .map { list -> list.map { it.toDto() } }
    }

    override suspend fun markTransactionsAsSynced(ids: List<String>): Result<Unit, DataError> {
        return localDataSource.markTransactionsAsSynced(ids)
    }

    override suspend fun searchTransactionsWithDetails(query: String): Result<List<Transaction>, DataError> {
        return localDataSource.searchTransactionsWithDetails(query)
            .map { list -> list.map { it.toDto() } }
    }

    override suspend fun getTransactionsByCategoryWithDetails(categoryId: String): Result<List<Transaction>, DataError> {
        return localDataSource.getTransactionsByCategoryWithDetails(categoryId)
            .map { list -> list.map { item -> item.toDto() } }
    }

    override suspend fun getTransactionsByPaymentModeWithDetails(paymentModeId: String): Result<List<Transaction>, DataError> {
        return localDataSource.getTransactionsByPaymentModeWithDetails(paymentModeId)
            .map { list -> list.map { item -> item.toDto() } }
    }
}