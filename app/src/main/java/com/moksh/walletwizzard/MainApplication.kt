package com.moksh.walletwizzard

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

@HiltAndroidApp
class MainApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
        if (BuildConfig.ENABLE_LOGGING) {
            Timber.plant(Timber.DebugTree())
        }
    }
}