package com.moksh.data.di

import android.content.Context
import com.moksh.data.database.WizzardDatabase
import com.moksh.data.datasource.CategoryDataSource
import com.moksh.data.datasource.ExpensesDataSource
import com.moksh.data.datasource.IncomeDataSource
import com.moksh.data.datasource.UserDataSource
import com.moksh.data.dto.CategoryDao
import com.moksh.data.dto.ExpenseDao
import com.moksh.data.dto.IncomeDao
import com.moksh.data.dto.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideWizzardDatabase(
        @ApplicationContext context: Context
    ): WizzardDatabase {
        return WizzardDatabase.getRoomDatabase(context)
    }

    @Provides
    @Singleton
    fun providesUserDataSource(userDao: UserDao): UserDataSource {
        return UserDataSource(userDao)
    }

    @Provides
    @Singleton
    fun providesIncomeDataSource(incomeDao: IncomeDao): IncomeDataSource {
        return IncomeDataSource(incomeDao)
    }

    @Provides
    @Singleton
    fun providesCategoryDataSource(categoryDao: CategoryDao): CategoryDataSource {
        return CategoryDataSource(categoryDao)
    }

    @Provides
    @Singleton
    fun providesExpenseDataSource(expenseDao: ExpenseDao): ExpensesDataSource {
        return ExpensesDataSource(expenseDao)
    }

}