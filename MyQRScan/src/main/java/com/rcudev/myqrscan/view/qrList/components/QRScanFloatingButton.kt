package com.rcudev.myqrscan.view.qrList.components

import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.journeyapps.barcodescanner.ScanOptions
import com.rcudev.myqrscan.MyQRScanApplication
import com.rcudev.myqrscan.R

@Composable
fun QRScanFloatingButton(
    application: MyQRScanApplication,
    onCreateQRClick: () -> Unit,
    onThemeChanged: (Boolean) -> Unit,
    barcodeLauncher: ActivityResultLauncher<ScanOptions>
) {

    @DrawableRes
    val icon: Int by animateIntAsState(if (application.isDarkTheme.value) R.drawable.ic_light_mode else R.drawable.ic_dark_mode)
    val backgroundColor by animateColorAsState(
        if (application.isDarkTheme.value) Color(0xFF29B6F6) else Color(
            0xFF303F9F
        )
    )

    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        FloatingActionButton(
            onClick = {
                // Empty
            },
            modifier = Modifier
                .width(44.dp)
                .height(44.dp)
                .align(Alignment.Bottom),
            backgroundColor = backgroundColor,
        ) {
            IconToggleButton(
                checked = application.isDarkTheme.value,
                onCheckedChange = {
                    application.toggleTheme()
                    onThemeChanged(application.isDarkTheme.value)
                }
            ) {
                Image(
                    painterResource(id = icon),
                    contentDescription = null
                )
            }
        }
        ExtendedFloatingActionButton(
            onClick = {
                val options = ScanOptions()
                options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
                options.setBeepEnabled(false)
                options.setBarcodeImageEnabled(true)
                barcodeLauncher.launch(options)
            },
            contentColor = Color.Black,
            backgroundColor = Color.White,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_qr_icon),
                    contentDescription = null
                )
            }, text = {
                Text(text = stringResource(id = R.string.qr_list_scan))
            }
        )
        FloatingActionButton(
            onClick = {
                onCreateQRClick()
            },
            modifier = Modifier
                .width(44.dp)
                .height(44.dp)
                .align(Alignment.Bottom),
            contentColor = Color.Black,
            backgroundColor = Color.White,
        ) {
            Icon(Icons.Rounded.Add, contentDescription = null)
        }
    }
}