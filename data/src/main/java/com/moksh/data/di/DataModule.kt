package com.moksh.data.di

import android.app.Activity
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.moksh.data.dao.CategoryDao
import com.moksh.data.dao.PaymentModeDao
import com.moksh.data.dao.TransactionDao
import com.moksh.data.dao.UserDao
import com.moksh.data.database.WizzardDatabase
import com.moksh.data.datasource.AuthDataSource
import com.moksh.data.datasource.CategoryDataSource
import com.moksh.data.datasource.PaymentModeDataSource
import com.moksh.data.datasource.TransactionDataSource
import com.moksh.data.datasource.UserDataSource
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
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun providesUserDataSource(userDao: UserDao): UserDataSource {
        return UserDataSource(userDao)
    }

    @Provides
    @Singleton
    fun providesIncomeDataSource(transactionDao: TransactionDao): TransactionDataSource {
        return TransactionDataSource(transactionDao)
    }

    @Provides
    @Singleton
    fun providesCategoryDataSource(categoryDao: CategoryDao): CategoryDataSource {
        return CategoryDataSource(categoryDao)
    }

    @Provides
    @Singleton
    fun providesPaymentModeDataSource(paymentModeDao: PaymentModeDao): PaymentModeDataSource {
        return PaymentModeDataSource(paymentModeDao)
    }

    @Provides
    @Singleton
    fun providesAuthDataSource(
        firebaseAuth: FirebaseAuth,
        userDataSource: UserDataSource,
        activity: Activity,
    ): AuthDataSource {
        return AuthDataSource(firebaseAuth, userDataSource, activity)
    }

}