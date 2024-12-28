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
class CategoryDaoTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: WizzardDatabase
    private lateinit var categoryDao: CategoryDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            WizzardDatabase::class.java
        ).build()
        categoryDao = database.categoryDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndGetCategory() = runTest {
        // Given
        val category = CategoryEntity(
            name = "Groceries",
            icon = "grocery_icon",
            color = "#FF0000",
            type = TransactionType.Expenses
        )

        // When
        categoryDao.insertCategory(category)

        // Then
        val loadedCategory = categoryDao.getCategoryById(category.id)
        assertThat(loadedCategory).isNotNull()
        assertThat(loadedCategory.name).isEqualTo("Groceries")
        assertThat(loadedCategory.type).isEqualTo(TransactionType.Expenses)
    }

    @Test
    fun getAllCategories() = runTest {
        // Given
        val categories = listOf(
            CategoryEntity(name = "Food", type = TransactionType.Expenses),
            CategoryEntity(name = "Salary", type = TransactionType.Income),
            CategoryEntity(name = "Transport", type = TransactionType.Expenses)
        )

        // When
        categories.forEach { categoryDao.insertCategory(it) }
        val allCategories = categoryDao.getAllCategories().first()

        // Then
        assertThat(allCategories).hasSize(3)
        // Verify they're ordered by name
        assertThat(allCategories.map { it.name })
            .isInOrder()
    }

    @Test
    fun getCategoriesByType() = runTest {
        // Given
        val expenseCategory = CategoryEntity(name = "Food", type = TransactionType.Expenses)
        val incomeCategory = CategoryEntity(name = "Salary", type = TransactionType.Income)

        // When
        categoryDao.insertCategory(expenseCategory)
        categoryDao.insertCategory(incomeCategory)

        val expenseCategories = categoryDao.getCategoriesByType(TransactionType.Expenses).first()

        // Then
        assertThat(expenseCategories).hasSize(1)
        assertThat(expenseCategories.first().name).isEqualTo("Food")
    }

    @Test
    fun updateCategory() = runTest {
        // Given
        val category = CategoryEntity(
            name = "Food",
            type = TransactionType.Expenses
        )
        categoryDao.insertCategory(category)

        // When
        val updatedCategory = category.copy(
            name = "Groceries",
            color = "#00FF00"
        )
        categoryDao.updateCategory(updatedCategory)

        // Then
        val loadedCategory = categoryDao.getCategoryById(category.id)
        assertThat(loadedCategory.name).isEqualTo("Groceries")
        assertThat(loadedCategory.color).isEqualTo("#00FF00")
    }

    @Test
    fun deleteCategory() = runTest {
        // Given
        val category = CategoryEntity(
            name = "Food",
            type = TransactionType.Expenses
        )
        categoryDao.insertCategory(category)

        // When
        categoryDao.deleteCategory(category)

        // Then
        val categoryCount = categoryDao.getCategoryCount()
        assertThat(categoryCount).isEqualTo(0)
    }

    @Test
    fun getMostUsedCategories() = runTest {
        // Given
        // First create some categories
        val foodCategory = CategoryEntity(
            name = "Food",
            type = TransactionType.Expenses
        )
        val transportCategory = CategoryEntity(
            name = "Transport",
            type = TransactionType.Expenses
        )
        val salaryCategory = CategoryEntity(
            name = "Salary",
            type = TransactionType.Income
        )

        categoryDao.insertCategory(foodCategory)
        categoryDao.insertCategory(transportCategory)
        categoryDao.insertCategory(salaryCategory)

        // Create test transactions
        val transactions = listOf(
            createTestTransaction(
                amount = 100.0,
                type = TransactionType.Expenses,
                categoryId = foodCategory.id,
                remark = "Lunch"
            ),
            createTestTransaction(
                amount = 200.0,
                type = TransactionType.Expenses,
                categoryId = foodCategory.id,
                remark = "Dinner"
            ),
            createTestTransaction(
                amount = 300.0,
                type = TransactionType.Expenses,
                categoryId = foodCategory.id,
                remark = "Groceries"
            ),
            createTestTransaction(
                amount = 150.0,
                type = TransactionType.Expenses,
                categoryId = transportCategory.id,
                remark = "Bus"
            ),
            createTestTransaction(
                amount = 250.0,
                type = TransactionType.Expenses,
                categoryId = transportCategory.id,
                remark = "Taxi"
            ),
            createTestTransaction(
                amount = 5000.0,
                type = TransactionType.Income,
                categoryId = salaryCategory.id,
                remark = "Monthly salary"
            )
        )

        val transactionDao = database.transactionDao()
        transactions.forEach { transactionDao.insertTransaction(it) }

        // When
        val mostUsedCategories = categoryDao.getMostUsedCategories(2)

        // Then
        assertThat(mostUsedCategories).hasSize(2)
        assertThat(mostUsedCategories[0].name).isEqualTo("Food") // 3 transactions
        assertThat(mostUsedCategories[1].name).isEqualTo("Transport") // 2 transactions
    }

    // Helper function to create test transactions
    private fun createTestTransaction(
        amount: Double,
        type: TransactionType,
        categoryId: String? = null,
        paymentModeId: String? = null,
        remark: String? = null,
        attachmentUri: String? = null,
        isSynced: Boolean = false
    ) = TransactionEntity(
        amount = amount,
        type = type,
        categoryId = categoryId,
        paymentModeId = paymentModeId,
        remark = remark,
        attachmentUri = attachmentUri,
        isSynced = isSynced,
        createdAt = Date(),
        updatedAt = Date()
    )

    // Add test for foreign key constraint
    @Test
    fun whenCategoryDeleted_transactionsCategoryIdSetToNull() = runTest {
        // Given
        val category = CategoryEntity(
            name = "Food",
            type = TransactionType.Expenses
        )
        categoryDao.insertCategory(category)

        val transaction = createTestTransaction(
            amount = 100.0,
            type = TransactionType.Expenses,
            categoryId = category.id,
            remark = "Test transaction"
        )

        val transactionDao = database.transactionDao()
        transactionDao.insertTransaction(transaction)

        // When
        categoryDao.deleteCategory(category)

        // Then
        val updatedTransaction = transactionDao.getTransactionById(transaction.id)
        assertThat(updatedTransaction.category?.id).isNull()
    }

    // Test cascading behavior
    @Test
    fun testForeignKeyConstraints() = runTest {
        // Given
        val category = CategoryEntity(
            name = "Food",
            type = TransactionType.Expenses
        )

        val paymentMode = PaymentModeEntity(
            name = "Cash"
        )

        categoryDao.insertCategory(category)
        database.paymentModeDao().insertPaymentMode(paymentMode)

        val transaction = createTestTransaction(
            amount = 100.0,
            type = TransactionType.Expenses,
            categoryId = category.id,
            paymentModeId = paymentMode.id,
            remark = "Test transaction"
        )

        val transactionDao = database.transactionDao()
        transactionDao.insertTransaction(transaction)

        // When category is deleted
        categoryDao.deleteCategory(category)

        // Then
        val transactionAfterCategoryDelete = transactionDao.getTransactionById(transaction.id)
        assertThat(transactionAfterCategoryDelete.category?.id).isNull()
        assertThat(transactionAfterCategoryDelete.paymentMode?.id).isEqualTo(paymentMode.id)

        // When payment mode is deleted
        database.paymentModeDao().deletePaymentMode(paymentMode)

        // Then
        val transactionAfterBothDeletes = transactionDao.getTransactionById(transaction.id)
        assertThat(transactionAfterBothDeletes.category?.id).isNull()
        assertThat(transactionAfterBothDeletes.paymentMode?.id).isNull()
    }
}