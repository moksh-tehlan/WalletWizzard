package com.moksh.data.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.moksh.data.database.WizzardDatabase
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
class PaymentModeDaoTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: WizzardDatabase
    private lateinit var paymentModeDao: PaymentModeDao
    private lateinit var transactionDao: TransactionDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            WizzardDatabase::class.java
        ).build()
        paymentModeDao = database.paymentModeDao()
        transactionDao = database.transactionDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    private fun createTestPaymentMode(
        name: String,
        icon: String? = null,
        isSynced: Boolean = false,
        createdAt: Date = Date(),
        updatedAt: Date = Date()
    ) = PaymentModeEntity(
        name = name,
        icon = icon,
        isSynced = isSynced,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    @Test
    fun insertAndGetPaymentMode() = runTest {
        // Given
        val paymentMode = createTestPaymentMode(
            name = "Cash",
            icon = "cash_icon"
        )

        // When
        paymentModeDao.insertPaymentMode(paymentMode)
        val loadedPaymentMode = paymentModeDao.getPaymentModeById(paymentMode.id)

        // Then
        assertThat(loadedPaymentMode).isNotNull()
        assertThat(loadedPaymentMode.name).isEqualTo("Cash")
        assertThat(loadedPaymentMode.icon).isEqualTo("cash_icon")
    }

    @Test
    fun getAllPaymentModes() = runTest {
        // Given
        val paymentModes = listOf(
            createTestPaymentMode("Cash"),
            createTestPaymentMode("Credit Card"),
            createTestPaymentMode("Bank Transfer")
        )
        paymentModes.forEach { paymentModeDao.insertPaymentMode(it) }

        // When
        val allPaymentModes = paymentModeDao.getAllPaymentModes().first()

        // Then
        assertThat(allPaymentModes).hasSize(3)
        // Verify they're ordered by name
        assertThat(allPaymentModes.map { it.name })
            .containsExactly("Bank Transfer", "Cash", "Credit Card")
            .inOrder()
    }

    @Test
    fun updatePaymentMode() = runTest {
        // Given
        val paymentMode = createTestPaymentMode(
            name = "Cash",
            icon = "cash_icon"
        )
        paymentModeDao.insertPaymentMode(paymentMode)

        // When
        val updatedPaymentMode = paymentMode.copy(
            name = "Updated Cash",
            icon = "new_cash_icon"
        )
        paymentModeDao.updatePaymentMode(updatedPaymentMode)

        // Then
        val loadedPaymentMode = paymentModeDao.getPaymentModeById(paymentMode.id)
        assertThat(loadedPaymentMode.name).isEqualTo("Updated Cash")
        assertThat(loadedPaymentMode.icon).isEqualTo("new_cash_icon")
    }

    @Test
    fun deletePaymentMode() = runTest {
        // Given
        val paymentMode = createTestPaymentMode("Cash")
        paymentModeDao.insertPaymentMode(paymentMode)

        // When
        paymentModeDao.deletePaymentMode(paymentMode)

        // Then
        val count = paymentModeDao.getPaymentModeCount()
        assertThat(count).isEqualTo(0)
    }

    @Test
    fun deletePaymentModeById() = runTest {
        // Given
        val paymentMode = createTestPaymentMode("Cash")
        paymentModeDao.insertPaymentMode(paymentMode)

        // When
        paymentModeDao.deletePaymentModeById(paymentMode.id)

        // Then
        val count = paymentModeDao.getPaymentModeCount()
        assertThat(count).isEqualTo(0)
    }

    @Test
    fun getUnsyncedPaymentModesAndMarkAsSynced() = runTest {
        // Given
        val unsyncedPaymentModes = listOf(
            createTestPaymentMode("Cash", isSynced = false),
            createTestPaymentMode("Card", isSynced = false)
        )
        val syncedPaymentMode = createTestPaymentMode("Bank", isSynced = true)

        unsyncedPaymentModes.forEach { paymentModeDao.insertPaymentMode(it) }
        paymentModeDao.insertPaymentMode(syncedPaymentMode)

        // When
        val unsynced = paymentModeDao.getUnsyncedPaymentModes()
        paymentModeDao.markPaymentModesAsSynced(unsynced.map { it.id })

        // Then
        assertThat(unsynced).hasSize(2)
        val newUnsynced = paymentModeDao.getUnsyncedPaymentModes()
        assertThat(newUnsynced).isEmpty()
    }

    @Test
    fun searchPaymentModes() = runTest {
        // Given
        val paymentModes = listOf(
            createTestPaymentMode("Cash"),
            createTestPaymentMode("Credit Card"),
            createTestPaymentMode("Debit Card"),
            createTestPaymentMode("Bank Transfer")
        )
        paymentModes.forEach { paymentModeDao.insertPaymentMode(it) }

        // When
        val searchResults = paymentModeDao.searchPaymentModes("Card")

        // Then
        assertThat(searchResults).hasSize(2)
        assertThat(searchResults.map { it.name })
            .containsExactly("Credit Card", "Debit Card")
            .inOrder()
    }

    @Test
    fun getMostUsedPaymentModes() = runTest {
        // Given
        val cash = createTestPaymentMode("Cash")
        val card = createTestPaymentMode("Card")
        val bank = createTestPaymentMode("Bank")

        paymentModeDao.insertPaymentMode(cash)
        paymentModeDao.insertPaymentMode(card)
        paymentModeDao.insertPaymentMode(bank)

        // Create transactions with different payment modes
        val transactions = listOf(
            TransactionEntity(
                amount = 100.0,
                type = TransactionType.Expenses,
                categoryId = null,
                paymentModeId = cash.id,
                remark = "Cash transaction 1",
                attachmentUri = null
            ),
            TransactionEntity(
                amount = 200.0,
                type = TransactionType.Expenses,
                categoryId = null,
                paymentModeId = cash.id,
                remark = "Cash transaction 2",
                attachmentUri = null
            ),
            TransactionEntity(
                amount = 300.0,
                type = TransactionType.Expenses,
                categoryId = null,
                paymentModeId = cash.id,
                remark = "Cash transaction 3",
                attachmentUri = null
            ),
            TransactionEntity(
                amount = 400.0,
                type = TransactionType.Expenses,
                categoryId = null,
                paymentModeId = card.id,
                remark = "Card transaction 1",
                attachmentUri = null
            ),
            TransactionEntity(
                amount = 500.0,
                type = TransactionType.Expenses,
                categoryId = null,
                paymentModeId = card.id,
                remark = "Card transaction 2",
                attachmentUri = null
            ),
            TransactionEntity(
                amount = 600.0,
                type = TransactionType.Expenses,
                categoryId = null,
                paymentModeId = bank.id,
                remark = "Bank transaction",
                attachmentUri = null
            )
        )

        transactions.forEach { transactionDao.insertTransaction(it) }

        // When
        val mostUsed = paymentModeDao.getMostUsedPaymentModes(2)

        // Then
        assertThat(mostUsed).hasSize(2)
        assertThat(mostUsed[0].name).isEqualTo("Cash") // 3 transactions
        assertThat(mostUsed[1].name).isEqualTo("Card") // 2 transactions
    }

    @Test
    fun getPaymentModesUpdatedAfter() = runTest {
        // Given
        val baseDate = Date()
        Thread.sleep(100) // Ensure some time passes

        val recentPaymentMode = createTestPaymentMode(
            name = "Recent Payment",
            updatedAt = Date()
        )
        val oldPaymentMode = createTestPaymentMode(
            name = "Old Payment",
            updatedAt = Date(baseDate.time - 1000) // 1 second before base date
        )

        paymentModeDao.insertPaymentMode(recentPaymentMode)
        paymentModeDao.insertPaymentMode(oldPaymentMode)

        // When
        val updatedPaymentModes = paymentModeDao.getPaymentModesUpdatedAfter(baseDate)

        // Then
        assertThat(updatedPaymentModes).hasSize(1)
        assertThat(updatedPaymentModes.first().name).isEqualTo("Recent Payment")
    }

    @Test
    fun getPaymentModesUpdatedAfter_withNoUpdates() = runTest {
        // Given
        val oldPaymentMode = createTestPaymentMode(
            name = "Old Payment",
            updatedAt = Date(System.currentTimeMillis() - 1000)
        )
        paymentModeDao.insertPaymentMode(oldPaymentMode)

        // When
        val updatedPaymentModes = paymentModeDao.getPaymentModesUpdatedAfter(Date())

        // Then
        assertThat(updatedPaymentModes).isEmpty()
    }

    @Test
    fun testForeignKeyConstraint_onTransactionTable() = runTest {
        // Given
        val paymentMode = createTestPaymentMode("Cash")
        paymentModeDao.insertPaymentMode(paymentMode)

        val transaction = TransactionEntity(
            amount = 100.0,
            type = TransactionType.Expenses,
            categoryId = null,
            paymentModeId = paymentMode.id,
            remark = "Test transaction",
            attachmentUri = null
        )
        transactionDao.insertTransaction(transaction)

        // When
        paymentModeDao.deletePaymentMode(paymentMode)

        // Then
        val updatedTransaction = transactionDao.getTransactionById(transaction.id)
        assertThat(updatedTransaction.transaction.paymentModeId).isNull()
        assertThat(updatedTransaction.paymentMode).isNull()
    }
}