package com.rcudev.myqrscan.view

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.rcudev.myqrscan.MyQRScanApplication
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRCategory
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.view.qrList.QRListScreen
import com.rcudev.myqrscan.view.qrList.QRListViewModel
import com.rcudev.myqrscan.view.theme.MyQRScanTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

val DARK_THEME_VALUE = booleanPreferencesKey("DARK_THEME_VALUE")

@AndroidEntryPoint
class MyQRScanMainActivity : ComponentActivity() {

    private val viewModel: QRListViewModel by viewModels()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    @Inject
    lateinit var application: MyQRScanApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.initViewModel(QRCategory(resources.getString(R.string.qr_recent_category)))
            application.isDarkTheme.value = getDarkMode()
            withContext(Dispatchers.Main) {
                setContent {
                    MyQRScanTheme(
                        darkTheme = application.isDarkTheme.value
                    ) {
                        Surface(modifier = Modifier.fillMaxSize()) {
                            QRListScreen(
                                application = application,
                                context = this@MyQRScanMainActivity,
                                viewModel = viewModel,
                                onThemeChanged = { darkModeValue ->
                                    lifecycleScope.launch {
                                        saveDarkMode(darkModeValue)
                                    }
                                }, barcodeLauncher
                            )
                        }
                    }
                }
            }
        }
    }

    private suspend fun getDarkMode(): Boolean {
        return dataStore.data.first()[DARK_THEME_VALUE] ?: true
    }

    private suspend fun saveDarkMode(darkModeState: Boolean) {
        dataStore.edit { darkModeSetting ->
            darkModeSetting[DARK_THEME_VALUE] = darkModeState
        }
    }

    private val barcodeLauncher =
        registerForActivityResult(ScanContract()) { result: ScanIntentResult ->
            if (result.contents != null) {
                viewModel.selectedCategory.value =
                    QRCategory(resources.getString(R.string.qr_recent_category))
                viewModel.saveQR(
                    QRItem(
                        null,
                        result.contents,
                        result.contents,
                        resources.getString(R.string.qr_recent_category)
                    )
                )
            }
        }

}

