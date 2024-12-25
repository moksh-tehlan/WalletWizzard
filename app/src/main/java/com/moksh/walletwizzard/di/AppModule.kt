package com.moksh.walletwizzard.di

import android.app.Activity
import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.moksh.walletwizzard.MainApplication
import com.moksh.walletwizzard.utils.ActivityRetainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences {
        return EncryptedSharedPreferences(
            app,
            "auth_pref",
            MasterKey(app),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    fun provideCoroutineScope(app: Application): CoroutineScope {
        return (app as MainApplication).applicationScope
    }

    @Provides
    @Singleton
    fun provideActivityRetainer() = ActivityRetainer()

    @Provides
    @Singleton
    fun provideActivity(activityRetainer : ActivityRetainer):Activity{
        return activityRetainer.getActivity() ?: throw IllegalStateException("Activity is null")
    }
}