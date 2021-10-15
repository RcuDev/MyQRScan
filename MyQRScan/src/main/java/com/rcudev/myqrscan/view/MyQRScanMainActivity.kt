package com.rcudev.myqrscan.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.zxing.integration.android.IntentIntegrator
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.view.qrList.QRListViewModel
import com.rcudev.myqrscan.view.qrList.components.QRListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyQRScanMainActivity : AppCompatActivity() {

    private val viewModel: QRListViewModel by viewModels()

    private lateinit var bannerAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_qr_scan_main)
        findViewById<ComposeView>(R.id.myqrscan_compose_container).setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                QRListScreen(this, viewModel)
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
        MobileAds.initialize(this) { }
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTagForChildDirectedTreatment(RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
                .setMaxAdContentRating(RequestConfiguration.MAX_AD_CONTENT_RATING_G)
                .build()
        )
        val adRequest = AdManagerAdRequest.Builder().build()
        bannerAdView.loadAd(adRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents != null) {
                viewModel.saveQR(QRItem(null, result.contents, result.contents))
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

