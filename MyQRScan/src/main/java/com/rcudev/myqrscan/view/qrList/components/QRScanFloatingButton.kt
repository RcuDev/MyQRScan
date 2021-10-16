package com.rcudev.myqrscan.view.qrList.components

import android.app.Activity
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.google.zxing.integration.android.IntentIntegrator
import com.rcudev.myqrscan.R

@Composable
fun QRScanFloatingButton(
    context: Activity
) {
    ExtendedFloatingActionButton(
        onClick = {
            val qrScanner = IntentIntegrator(context)
            qrScanner.setBarcodeImageEnabled(true)
            qrScanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            qrScanner.setBeepEnabled(true)
            qrScanner.initiateScan()
        },
        contentColor = Color.White,
        backgroundColor = Color.Red,
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_qr_icon),
                contentDescription = null
            )
        }, text = {
            Text(text = stringResource(id = R.string.qr_list_scan))
        }
    )
}