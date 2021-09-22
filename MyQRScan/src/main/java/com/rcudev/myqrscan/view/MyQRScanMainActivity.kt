package com.rcudev.myqrscan.view

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import com.google.android.gms.ads.*
import com.google.zxing.integration.android.IntentIntegrator
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.view.qrList.RecentScanViewModel
import com.rcudev.myqrscan.view.qrList.components.RecentScanScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyQRScanMainActivity : ComponentActivity() {

    private val viewModel: RecentScanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_qr_scan_main)
        findViewById<ComposeView>(R.id.myqrscan_compose_container).setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                RecentScanScreen(this, viewModel)
            }
        }

        initAdMob()
    }

    private fun initAdMob() {
        MobileAds.initialize(this) { }
        val adRequest = AdRequest.Builder().build()
        val bannerAdView: AdView = findViewById(R.id.myqrscan_admob_banner)
        bannerAdView.loadAd(adRequest)
        bannerAdView.adListener = object : AdListener() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                bannerAdView.visibility = GONE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.saveQR(QRItem(null, result.contents, result.contents))
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

