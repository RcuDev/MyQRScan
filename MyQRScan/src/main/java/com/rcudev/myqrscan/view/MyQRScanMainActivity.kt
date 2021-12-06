package com.rcudev.myqrscan.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View.VISIBLE
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.google.android.gms.ads.*
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
import javax.inject.Inject


@AndroidEntryPoint
class MyQRScanMainActivity : ComponentActivity() {

    private val viewModel: QRListViewModel by viewModels()

    private lateinit var sharedPref: SharedPreferences
    private lateinit var bannerAdView: AdView

    @Inject
    lateinit var application: MyQRScanApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_qr_scan_main)

        sharedPref = this.getSharedPreferences("THEME_MODE", Context.MODE_PRIVATE)
        application.isDarkTheme.value = sharedPref.getBoolean("DARK_THEME_VALUE", false)
        viewModel.initViewModel(QRCategory(resources.getString(R.string.qr_recent_category)))

        findViewById<ComposeView>(R.id.myqrscan_compose_container).setContent {
            MyQRScanTheme(
                darkTheme = application.isDarkTheme.value
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    QRListScreen(application, this, viewModel,
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

        MobileAds.initialize(this) {}
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf("30772B424E3E9B43E0BDAF4D84B807C8"))
                .build()
        )
        initAdMob()
    }

    public override fun onPause() {
        bannerAdView.pause()
        super.onPause()
    }

    public override fun onResume() {
        super.onResume()
        bannerAdView.resume()
    }

    public override fun onDestroy() {
        bannerAdView.destroy()
        super.onDestroy()
    }

    private fun initAdMob() {
        bannerAdView = findViewById(R.id.myqrscan_admob_banner)
        val adRequest = AdRequest.Builder().build()
        bannerAdView.loadAd(adRequest)
        bannerAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                bannerAdView.visibility = VISIBLE
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

