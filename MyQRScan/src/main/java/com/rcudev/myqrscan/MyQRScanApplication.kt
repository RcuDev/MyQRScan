package com.rcudev.myqrscan

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyQRScanApplication : Application() {

    var isDarkTheme = mutableStateOf(false)

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }
}