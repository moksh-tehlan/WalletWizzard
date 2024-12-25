package com.moksh.data.di

import com.moksh.data.repository.AuthRepositoryImpl
import com.moksh.data.repository.CategoryRepositoryImpl
import com.moksh.data.repository.PaymentModeRepositoryImpl
import com.moksh.data.repository.TransactionRepositoryImpl
import com.moksh.data.repository.UserRepositoryImpl
import com.moksh.domain.repository.AuthRepository
import com.moksh.domain.repository.CategoryRepository
import com.moksh.domain.repository.PaymentModeRepository
import com.moksh.domain.repository.TransactionRepository
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
    abstract fun bindTransactionRepositoryImpl(
        transactionRepositoryImpl: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindPaymentModeRepositoryImpl(
        paymentRepositoryImpl: PaymentModeRepositoryImpl
    ): PaymentModeRepository


    @Binds
    @Singleton
    abstract fun bindCategoryRepositoryImpl(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepositoryImpl(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}
