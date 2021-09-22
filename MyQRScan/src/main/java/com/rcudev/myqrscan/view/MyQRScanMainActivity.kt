package com.rcudev.myqrscan.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.zxing.integration.android.IntentIntegrator
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.view.qrList.RecentScanViewModel
import com.rcudev.myqrscan.view.qrList.components.RecentScanScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyQRScanMainActivity : ComponentActivity() {

    private val viewModel: RecentScanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecentScanScreen(this, viewModel)
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

