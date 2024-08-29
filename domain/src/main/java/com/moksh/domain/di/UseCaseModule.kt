package com.moksh.domain.di

import com.moksh.domain.repository.ExpenseRepository
import com.moksh.domain.repository.IncomeRepository
import com.moksh.domain.repository.UserRepository
import com.moksh.domain.usecases.expense.GetExpenses
import com.moksh.domain.usecases.expense.SaveExpense
import com.moksh.domain.usecases.income.GetIncome
import com.moksh.domain.usecases.income.SaveIncome
import com.moksh.domain.usecases.user.GetUser
import com.moksh.domain.usecases.user.SaveUser
import com.moksh.domain.usecases.user.UpdateUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideGetUserUseCase(userRepository: UserRepository) =
        GetUser(userRepository = userRepository)

    @Provides
    @Singleton
    fun provideSaveUserUseCase(userRepository: UserRepository) =
        SaveUser(userRepository = userRepository)

    @Provides
    @Singleton
    fun provideUpdateUserUseCase(userRepository: UserRepository) =
        UpdateUser(userRepository = userRepository)

    @Provides
    @Singleton
    fun provideGetIncomeUseCase(incomeRepository: IncomeRepository) =
        GetIncome(incomeRepository = incomeRepository)

    @Provides
    @Singleton
    fun provideSaveIncomeUseCase(incomeRepository: IncomeRepository) =
        SaveIncome(incomeRepository = incomeRepository)

    @Provides
    @Singleton
    fun provideGetExpensesUseCase(expenseRepository: ExpenseRepository) =
        GetExpenses(expenseRepository = expenseRepository)

    @Provides
    @Singleton
    fun provideSaveExpenseUseCase(expenseRepository: ExpenseRepository) =
        SaveExpense(expenseRepository = expenseRepository)
}