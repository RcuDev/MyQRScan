package com.rcudev.myqrscan.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.zxing.integration.android.IntentIntegrator
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
class MyQRScanMainActivity : AppCompatActivity() {

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
                        })
                }
            }
        }

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
        //MobileAds.initialize(this) { }
        //val adRequest = AdRequest.Builder().build()
        //bannerAdView.loadAd(adRequest)
        bannerAdView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                bannerAdView.visibility = VISIBLE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents != null) {
                viewModel.selectedCategory.value = QRCategory("Recent")
                viewModel.saveQR(QRItem(null, result.contents, result.contents, "Recent"))
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

