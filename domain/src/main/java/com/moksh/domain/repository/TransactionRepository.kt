package com.moksh.domain.repository

import com.moksh.domain.model.response.SaveTransaction
import com.moksh.domain.model.response.Transaction
import com.moksh.domain.model.response.TransactionType
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface TransactionRepository {
    suspend fun insertTransaction(transaction: SaveTransaction): Result<String, DataError>

    fun getAllTransactionsWithDetails(): Result<Flow<List<Transaction>>, DataError>

    fun getTransactionsByTypeWithDetails(type: TransactionType): Result<Flow<List<Transaction>>, DataError>

    suspend fun getTransactionById(id: String): Result<Transaction?, DataError>

    suspend fun updateTransaction(transaction: Transaction): Result<Unit, DataError>

    suspend fun deleteTransaction(transaction: Transaction): Result<Unit, DataError>

    suspend fun deleteTransactionById(id: String): Result<Unit, DataError>

    suspend fun getTransactionsBetweenDates(startDate: Date, endDate: Date): Result<List<Transaction>, DataError>

    suspend fun getTotalAmountByType(type: TransactionType): Result<Double, DataError>

    suspend fun getTotalAmountByTypeAndDateRange(type: TransactionType, startDate: Date, endDate: Date): Result<Double, DataError>

    suspend fun getTransactionCount(): Result<Int, DataError>

    suspend fun getUnsyncedTransactions(): Result<List<Transaction>, DataError>

    suspend fun markTransactionsAsSynced(ids: List<String>): Result<Unit, DataError>

    suspend fun searchTransactionsWithDetails(query: String): Result<List<Transaction>, DataError>

    suspend fun getTransactionsByCategoryWithDetails(categoryId: String): Result<List<Transaction>, DataError>

    suspend fun getTransactionsByPaymentModeWithDetails(paymentModeId: String): Result<List<Transaction>, DataError>
}