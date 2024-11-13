package com.moksh.data

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.moksh.data.database.WizzardDatabase
import com.moksh.data.dao.CategoryDao
import com.moksh.data.dao.UserDao
import com.moksh.data.entities.local.CategoryEntity
import com.moksh.data.entities.local.ExpenseEntity
import com.moksh.data.entities.local.IncomeEntity
import com.moksh.data.entities.local.UserEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoomTest {
    private lateinit var db: WizzardDatabase
    private lateinit var userDao: UserDao
    private lateinit var incomeDao: IncomeDao
    private lateinit var expenseDao: ExpenseDao
    private lateinit var categoryDao: CategoryDao

    @Before
    fun setUp() {
        db = WizzardDatabase.getRoomDatabase(ApplicationProvider.getApplicationContext())
        userDao = db.userDao()
        incomeDao = db.incomeDao()
        expenseDao = db.expenseDao()
        categoryDao = db.categoryDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertCategories() = runBlocking {
        val salaryCategory = CategoryEntity(
            name = "Salary"
        )

        val rentCategory = CategoryEntity(
            name = "Rent"
        )
        categoryDao.insertCategory(salaryCategory)
        categoryDao.insertCategory(rentCategory)
    }

    @Test
    fun insertUser() = runBlocking {
        val user = UserEntity(
            name = "Moksh",
            email = "tehlanmoksh@gmail.com",
            phoneNumber = "1234567890",
        )
        val insertedUser = userDao.insertUser(user)
        assert(insertedUser.name == user.name)
    }

    @Test
    fun insertIncome() = runBlocking {
        val salaryCategory = categoryDao.getCategoryByName("Salary")
        val income = IncomeEntity(
            amount = 1000.0,
            date = "2021-09-01",
            remark = "Salary came from grappus",
            categoryId = salaryCategory.id,
            paymentMode = PaymentMode.ONLINE
        )
        val incomeId = incomeDao.insertIncome(income)
        assert(incomeId > 0L)
    }

    @Test
    fun insertExpenses() = runBlocking {
        val rentCategory = categoryDao.getCategoryByName("Rent")
        val expense = ExpenseEntity(
            amount = 1000.0,
            date = "2021-09-01",
            remark = "Paid rent for the month of September",
            categoryId = rentCategory.id,
            paymentMode = PaymentMode.CASH
        )
        val expenseId = expenseDao.insertExpense(expense)
        assert(expenseId > 0L)
    }
}