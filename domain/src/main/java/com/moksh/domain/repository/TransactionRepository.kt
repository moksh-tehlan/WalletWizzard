package com.moksh.domain.repository

import com.moksh.domain.model.response.SaveTransaction
import com.moksh.domain.model.response.Transaction
import com.moksh.domain.model.response.TransactionType
import com.moksh.domain.util.DataError
import com.moksh.domain.util.Result
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface TransactionRepository {
    suspend fun insertTransaction(transaction: SaveTransaction): Result<String, DataError.Local>

    fun getAllTransactionsWithDetails(): Result<Flow<List<Transaction>>, DataError.Local>

    fun getTransactionsByTypeWithDetails(type: TransactionType): Result<Flow<List<Transaction>>, DataError.Local>

    suspend fun getTransactionById(id: String): Result<Transaction?, DataError.Local>

    suspend fun updateTransaction(transaction: Transaction): Result<Unit, DataError.Local>

    suspend fun deleteTransaction(transaction: Transaction): Result<Unit, DataError.Local>

    suspend fun deleteTransactionById(id: String): Result<Unit, DataError.Local>

    suspend fun getTransactionsBetweenDates(startDate: Date, endDate: Date): Result<List<Transaction>, DataError.Local>

    suspend fun getTotalAmountByType(type: TransactionType): Result<Double, DataError.Local>

    suspend fun getTotalAmountByTypeAndDateRange(type: TransactionType, startDate: Date, endDate: Date): Result<Double, DataError.Local>

    suspend fun getTransactionCount(): Result<Int, DataError.Local>

    suspend fun getUnsyncedTransactions(): Result<List<Transaction>, DataError.Local>

    suspend fun markTransactionsAsSynced(ids: List<String>): Result<Unit, DataError.Local>

    suspend fun searchTransactionsWithDetails(query: String): Result<List<Transaction>, DataError.Local>

    suspend fun getTransactionsByCategoryWithDetails(categoryId: String): Result<List<Transaction>, DataError.Local>

    suspend fun getTransactionsByPaymentModeWithDetails(paymentModeId: String): Result<List<Transaction>, DataError.Local>
}