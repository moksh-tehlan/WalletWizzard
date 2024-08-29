package com.moksh.data.di

import com.moksh.data.database.WizzardDatabase
import com.moksh.data.dto.CategoryDao
import com.moksh.data.dto.ExpenseDao
import com.moksh.data.dto.IncomeDao
import com.moksh.data.dto.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Singleton
    @Provides
    fun provideUserDao(wizzardDatabase: WizzardDatabase): UserDao {
        return wizzardDatabase.userDao()
    }

    @Singleton
    @Provides
    fun provideIncomeDao(wizzardDatabase: WizzardDatabase): IncomeDao {
        return wizzardDatabase.incomeDao()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(wizzardDatabase: WizzardDatabase): CategoryDao {
        return wizzardDatabase.categoryDao()
    }

    @Singleton
    @Provides
    fun provideExpenseDao(wizzardDatabase: WizzardDatabase): ExpenseDao {
        return wizzardDatabase.expenseDao()
    }
}