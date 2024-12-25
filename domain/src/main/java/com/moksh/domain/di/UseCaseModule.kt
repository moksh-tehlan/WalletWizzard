package com.moksh.domain.di

import com.moksh.domain.repository.CategoryRepository
import com.moksh.domain.repository.PaymentModeRepository
import com.moksh.domain.repository.TransactionRepository
import com.moksh.domain.repository.UserRepository
import com.moksh.domain.usecases.category.GetCategories
import com.moksh.domain.usecases.category.InsertDefaultCategory
import com.moksh.domain.usecases.expense.GetExpenses
import com.moksh.domain.usecases.expense.SaveExpense
import com.moksh.domain.usecases.income.GetIncome
import com.moksh.domain.usecases.income.SaveIncome
import com.moksh.domain.usecases.payment_mode.GetPaymentModes
import com.moksh.domain.usecases.payment_mode.InsertDefaultPaymentMode
import com.moksh.domain.usecases.payment_mode.SavePaymentMode
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
    fun provideGetIncomeUseCase(incomeRepository: TransactionRepository) =
        GetIncome(incomeRepository = incomeRepository)

    @Provides
    @Singleton
    fun provideSaveIncomeUseCase(incomeRepository: TransactionRepository) =
        SaveIncome(incomeRepository = incomeRepository)

    @Provides
    @Singleton
    fun provideGetExpensesUseCase(expenseRepository: TransactionRepository) =
        GetExpenses(expenseRepository = expenseRepository)

    @Provides
    @Singleton
    fun provideSaveExpenseUseCase(expenseRepository: TransactionRepository) =
        SaveExpense(expenseRepository = expenseRepository)

    @Provides
    @Singleton
    fun provideInsertDefaultPaymentModeUseCase(paymentModeRepository: PaymentModeRepository) =
        InsertDefaultPaymentMode(paymentModeRepository = paymentModeRepository)

    @Provides
    @Singleton
    fun provideGetPaymentModesUseCase(paymentModeRepository: PaymentModeRepository) =
        GetPaymentModes(paymentModeRepository = paymentModeRepository)

    @Provides
    @Singleton
    fun provideSavePaymentModeUseCase(paymentModeRepository: PaymentModeRepository) =
        SavePaymentMode(paymentModeRepository = paymentModeRepository)

    @Provides
    @Singleton
    fun provideInsertDefaultCategories(categoryRepository: CategoryRepository) =
        InsertDefaultCategory(categoryRepository = categoryRepository)


    @Provides
    @Singleton
    fun provideGetExpensesCategories(categoryRepository: CategoryRepository) =
        GetCategories(categoryRepository = categoryRepository)
}