package com.moksh.data.di

import com.moksh.data.repository.ExpenseRepositoryImpl
import com.moksh.data.repository.IncomeRepositoryImpl
import com.moksh.data.repository.UserRepositoryImpl
import com.moksh.domain.repository.ExpenseRepository
import com.moksh.domain.repository.IncomeRepository
import com.moksh.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserRepositoryImpl(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindIncomeRepositoryImpl(
        incomeRepository: IncomeRepositoryImpl
    ): IncomeRepository

    @Binds
    @Singleton
    abstract fun bindExpenseRepositoryImpl(
        expenseRepositoryImpl: ExpenseRepositoryImpl
    ): ExpenseRepository
}
