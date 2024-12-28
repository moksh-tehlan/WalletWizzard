package com.moksh.data.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.moksh.data.database.WizzardDatabase
import com.moksh.data.entities.local.CategoryEntity
import com.moksh.data.entities.local.PaymentModeEntity
import com.moksh.data.entities.local.TransactionEntity
import com.moksh.domain.model.response.TransactionType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class TransactionDaoTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: WizzardDatabase
    private lateinit var transactionDao: TransactionDao
    private lateinit var categoryDao: CategoryDao
    private lateinit var paymentModeDao: PaymentModeDao

    private lateinit var foodCategory: CategoryEntity
    private lateinit var transportCategory: CategoryEntity
    private lateinit var cashPaymentMode: PaymentModeEntity
    private lateinit var cardPaymentMode: PaymentModeEntity

    @Before
    fun setup() = runTest {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            WizzardDatabase::class.java
        ).build()

        transactionDao = database.transactionDao()
        categoryDao = database.categoryDao()
        paymentModeDao = database.paymentModeDao()

        // Setup test data
        foodCategory = CategoryEntity(
            name = "Food",
            type = TransactionType.Expenses,
            icon = "food_icon",
            color = "#FF0000"
        )
        transportCategory = CategoryEntity(
            name = "Transport",
            type = TransactionType.Expenses,
            icon = "transport_icon",
            color = "#00FF00"
        )

        cashPaymentMode = PaymentModeEntity(
            name = "Cash",
            icon = "cash_icon"
        )
        cardPaymentMode = PaymentModeEntity(
            name = "Card",
            icon = "card_icon"
        )

        // Insert test data
        categoryDao.insertCategory(foodCategory)
        categoryDao.insertCategory(transportCategory)
        paymentModeDao.insertPaymentMode(cashPaymentMode)
        paymentModeDao.insertPaymentMode(cardPaymentMode)
    }

    @After
    fun teardown() {
        database.close()
    }

    private fun createTestTransaction(
        amount: Double,
        type: TransactionType = TransactionType.Expenses,
        categoryId: String? = null,
        paymentModeId: String? = null,
        remark: String? = null,
        attachmentUri: String? = null,
        isSynced: Boolean = false,
        createdAt: Date = Date()
    ) = TransactionEntity(
        amount = amount,
        type = type,
        categoryId = categoryId,
        paymentModeId = paymentModeId,
        remark = remark,
        attachmentUri = attachmentUri,
        isSynced = isSynced,
        createdAt = createdAt,
        updatedAt = createdAt
    )

    @Test
    fun insertAndGetTransactionWithDetails() = runTest {
        // Given
        val transaction = createTestTransaction(
            amount = 100.0,
            categoryId = foodCategory.id,
            paymentModeId = cashPaymentMode.id,
            remark = "Lunch"
        )

        // When
        transactionDao.insertTransaction(transaction)
        val loadedTransaction = transactionDao.getTransactionById(transaction.id)

        // Then
        assertThat(loadedTransaction.transaction.amount).isEqualTo(100.0)
        assertThat(loadedTransaction.category?.name).isEqualTo("Food")
        assertThat(loadedTransaction.paymentMode?.name).isEqualTo("Cash")
    }

    @Test
    fun getAllTransactionsWithDetails() = runTest {
        // Given
        val transactions = listOf(
            createTestTransaction(100.0, categoryId = foodCategory.id, paymentModeId = cashPaymentMode.id),
            createTestTransaction(200.0, categoryId = transportCategory.id, paymentModeId = cardPaymentMode.id)
        )
        transactions.forEach { transactionDao.insertTransaction(it) }

        // When
        val allTransactions = transactionDao.getAllTransactionsWithDetails().first()

        // Then
        assertThat(allTransactions).hasSize(2)
        assertThat(allTransactions.map { it.transaction.amount })
            .containsExactly(200.0, 100.0) // Ordered by created_at DESC
    }

    @Test
    fun getTransactionsByTypeWithDetails() = runTest {
        // Given
        val expenses = listOf(
            createTestTransaction(100.0, TransactionType.Expenses),
            createTestTransaction(200.0, TransactionType.Expenses)
        )
        val income = createTestTransaction(1000.0, TransactionType.Income)

        expenses.forEach { transactionDao.insertTransaction(it) }
        transactionDao.insertTransaction(income)

        // When
        val expenseTransactions = transactionDao.getTransactionsByTypeWithDetails(TransactionType.Expenses).first()

        // Then
        assertThat(expenseTransactions).hasSize(2)
        assertThat(expenseTransactions.map { it.transaction.type })
            .containsExactly(TransactionType.Expenses, TransactionType.Expenses)
    }

    @Test
    fun getTransactionsBetweenDates() = runTest {
        // Given
        val baseDate = Date()
        val pastDate = Date(baseDate.time - 5000) // 5 seconds ago
        val futureDate = Date(baseDate.time + 5000) // 5 seconds in future

        val transaction = createTestTransaction(
            amount = 100.0,
            createdAt = baseDate
        )
        transactionDao.insertTransaction(transaction)

        // When
        val transactions = transactionDao.getTransactionsBetweenDates(pastDate, futureDate)

        // Then
        assertThat(transactions).hasSize(1)
    }

    @Test
    fun getTransactionsByCategoryWithDetails() = runTest {
        // Given
        val foodTransactions = listOf(
            createTestTransaction(100.0, categoryId = foodCategory.id),
            createTestTransaction(200.0, categoryId = foodCategory.id)
        )
        val transportTransaction = createTestTransaction(300.0, categoryId = transportCategory.id)

        foodTransactions.forEach { transactionDao.insertTransaction(it) }
        transactionDao.insertTransaction(transportTransaction)

        // When
        val transactions = transactionDao.getTransactionsByCategoryWithDetails(foodCategory.id)

        // Then
        assertThat(transactions).hasSize(2)
        assertThat(transactions.all { it.category?.id == foodCategory.id }).isTrue()
    }

    @Test
    fun getTransactionsByPaymentModeWithDetails() = runTest {
        // Given
        val cashTransactions = listOf(
            createTestTransaction(100.0, paymentModeId = cashPaymentMode.id),
            createTestTransaction(200.0, paymentModeId = cashPaymentMode.id)
        )
        val cardTransaction = createTestTransaction(300.0, paymentModeId = cardPaymentMode.id)

        cashTransactions.forEach { transactionDao.insertTransaction(it) }
        transactionDao.insertTransaction(cardTransaction)

        // When
        val transactions = transactionDao.getTransactionsByPaymentModeWithDetails(cashPaymentMode.id)

        // Then
        assertThat(transactions).hasSize(2)
        assertThat(transactions.all { it.paymentMode?.id == cashPaymentMode.id }).isTrue()
    }

    @Test
    fun updateTransaction() = runTest {
        // Given
        val transaction = createTestTransaction(
            amount = 100.0,
            categoryId = foodCategory.id,
            remark = "Initial remark"
        )
        transactionDao.insertTransaction(transaction)

        // When
        val updatedTransaction = transaction.copy(
            amount = 150.0,
            remark = "Updated remark"
        )
        transactionDao.updateTransaction(updatedTransaction)

        // Then
        val loadedTransaction = transactionDao.getTransactionById(transaction.id)
        assertThat(loadedTransaction.transaction.amount).isEqualTo(150.0)
        assertThat(loadedTransaction.transaction.remark).isEqualTo("Updated remark")
    }

    @Test
    fun deleteTransaction() = runTest {
        // Given
        val transaction = createTestTransaction(100.0)
        transactionDao.insertTransaction(transaction)

        // When
        transactionDao.deleteTransaction(transaction)

        // Then
        assertThat(transactionDao.getTransactionCount()).isEqualTo(0)
    }

    @Test
    fun deleteTransactionById() = runTest {
        // Given
        val transaction = createTestTransaction(100.0)
        transactionDao.insertTransaction(transaction)

        // When
        transactionDao.deleteTransactionById(transaction.id)

        // Then
        assertThat(transactionDao.getTransactionCount()).isEqualTo(0)
    }

    @Test
    fun getTotalAmountByType() = runTest {
        // Given
        val expenses = listOf(
            createTestTransaction(100.0, TransactionType.Expenses),
            createTestTransaction(200.0, TransactionType.Expenses)
        )
        expenses.forEach { transactionDao.insertTransaction(it) }

        // When
        val totalExpenses = transactionDao.getTotalAmountByType(TransactionType.Expenses)

        // Then
        assertThat(totalExpenses).isEqualTo(300.0)
    }

    @Test
    fun getTotalAmountByTypeAndDateRange() = runTest {
        // Given
        val baseDate = Date()
        val startDate = Date(baseDate.time - 5000)
        val endDate = Date(baseDate.time + 5000)

        val transactions = listOf(
            createTestTransaction(100.0, TransactionType.Expenses, createdAt = baseDate),
            createTestTransaction(200.0, TransactionType.Expenses, createdAt = baseDate)
        )
        transactions.forEach { transactionDao.insertTransaction(it) }

        // When
        val totalAmount = transactionDao.getTotalAmountByTypeAndDateRange(
            TransactionType.Expenses,
            startDate,
            endDate
        )

        // Then
        assertThat(totalAmount).isEqualTo(300.0)
    }

    @Test
    fun getUnsyncedTransactionsAndMarkAsSynced() = runTest {
        // Given
        val unsyncedTransactions = listOf(
            createTestTransaction(100.0, isSynced = false),
            createTestTransaction(200.0, isSynced = false)
        )
        val syncedTransaction = createTestTransaction(300.0, isSynced = true)

        unsyncedTransactions.forEach { transactionDao.insertTransaction(it) }
        transactionDao.insertTransaction(syncedTransaction)

        // When
        val unsynced = transactionDao.getUnsyncedTransactions()
        transactionDao.markTransactionsAsSynced(unsynced.map { it.transaction.id })

        // Then
        assertThat(unsynced).hasSize(2)
        assertThat(transactionDao.getUnsyncedTransactions()).isEmpty()
    }

    @Test
    fun searchTransactionsWithDetails() = runTest {
        // Given
        val transactions = listOf(
            createTestTransaction(100.0, remark = "Lunch at restaurant"),
            createTestTransaction(200.0, remark = "Dinner at home"),
            createTestTransaction(300.0, remark = "Transport")
        )
        transactions.forEach { transactionDao.insertTransaction(it) }

        // When
        val searchResults = transactionDao.searchTransactionsWithDetails("at")

        // Then
        assertThat(searchResults).hasSize(2)
        assertThat(searchResults.map { it.transaction.remark })
            .containsExactly("Dinner at home", "Lunch at restaurant") // Ordered by created_at DESC
    }

    @Test
    fun foreignKeyConstraints() = runTest {
        // Given
        val transaction = createTestTransaction(
            100.0,
            categoryId = foodCategory.id,
            paymentModeId = cashPaymentMode.id
        )
        transactionDao.insertTransaction(transaction)

        // When category is deleted
        categoryDao.deleteCategory(foodCategory)

        // Then
        val afterCategoryDelete = transactionDao.getTransactionById(transaction.id)
        assertThat(afterCategoryDelete.transaction.categoryId).isNull()
        assertThat(afterCategoryDelete.category).isNull()
        assertThat(afterCategoryDelete.paymentMode).isNotNull()

        // When payment mode is deleted
        paymentModeDao.deletePaymentMode(cashPaymentMode)

        // Then
        val afterPaymentModeDelete = transactionDao.getTransactionById(transaction.id)
        assertThat(afterPaymentModeDelete.transaction.paymentModeId).isNull()
        assertThat(afterPaymentModeDelete.paymentMode).isNull()
    }
}