package com.rcudev.myqrscan.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MyQRScanMainActivity : ComponentActivity() {

    private val viewModel: QRListViewModel by viewModels()

    private lateinit var sharedPref: SharedPreferences

    @Inject
    lateinit var application: MyQRScanApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref =
            this@MyQRScanMainActivity.getSharedPreferences("THEME_MODE", Context.MODE_PRIVATE)
        viewModel.initViewModel(QRCategory(resources.getString(R.string.qr_recent_category)))

        lifecycleScope.launch(Dispatchers.IO) {
            application.isDarkTheme.value = sharedPref.getBoolean("DARK_THEME_VALUE", false)

            withContext(Dispatchers.Main) {
                setContent {
                    MyQRScanTheme(
                        darkTheme = application.isDarkTheme.value
                    ) {
                        Surface(modifier = Modifier.fillMaxSize()) {
                            QRListScreen(
                                application, this@MyQRScanMainActivity, viewModel,
                                onThemeChanged = {
                                    with(sharedPref.edit()) {
                                        putBoolean("DARK_THEME_VALUE", it)
                                        apply()
                                    }
                                }, barcodeLauncher
                            )
                        }
                    }
                }
            }
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

