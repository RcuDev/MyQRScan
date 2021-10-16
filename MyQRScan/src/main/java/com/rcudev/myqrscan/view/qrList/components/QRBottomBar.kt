package com.rcudev.myqrscan.view.qrList.components

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rcudev.myqrscan.view.qrList.QRListViewModel

@Composable
fun QRBottomBar(
    context: Context,
    viewModel: QRListViewModel
) {
    BottomAppBar(
        backgroundColor = Color.White,
        cutoutShape = MaterialTheme.shapes.small.copy(
            CornerSize(percent = 50)
        )
    ) {
        Switch(
            modifier = Modifier.padding(start = 15.dp),
            checked = false,
            onCheckedChange = {

            }
        )
    }
}