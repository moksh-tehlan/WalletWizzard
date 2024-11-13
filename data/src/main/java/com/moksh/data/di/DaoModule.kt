package com.moksh.data.di

import com.moksh.data.database.WizzardDatabase
import com.moksh.data.dao.CategoryDao
import com.moksh.data.dao.PaymentModeDao
import com.moksh.data.dao.TransactionDao
import com.moksh.data.dao.UserDao
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
    fun provideIncomeDao(wizzardDatabase: WizzardDatabase): TransactionDao {
        return wizzardDatabase.transactionDao()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(wizzardDatabase: WizzardDatabase): CategoryDao {
        return wizzardDatabase.categoryDao()
    }

    @Singleton
    @Provides
    fun providePaymentModeDao(wizzardDatabase: WizzardDatabase): PaymentModeDao {
        return wizzardDatabase.paymentModeDao()
    }

}