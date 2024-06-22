package com.moksh.data.di

import com.moksh.data.repository.MokshRepositoryImpl
import com.moksh.domain.repository.MokshRepository
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
    abstract fun bindMokshRepository(
        mokshRepositoryImpl: MokshRepositoryImpl
    ): MokshRepository
}