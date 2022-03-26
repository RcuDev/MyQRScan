package com.rcudev.myqrscan

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyQRScanApplication : Application() {

    var isDarkTheme = mutableStateOf(false)

    override fun onCreate() {
        StrictMode.setThreadPolicy(
            ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )
        super.onCreate()
    }

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
}