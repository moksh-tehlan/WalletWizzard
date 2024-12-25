package com.moksh.walletwizzard.utils

import android.app.Activity
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityRetainer @Inject constructor() {
    private var currentActivity: WeakReference<Activity>? = null

    fun setActivity(activity: Activity) {
        currentActivity = WeakReference(activity)
    }

    fun getActivity(): Activity? = currentActivity?.get()
}