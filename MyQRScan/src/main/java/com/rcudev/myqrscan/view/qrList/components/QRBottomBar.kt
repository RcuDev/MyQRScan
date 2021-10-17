package com.rcudev.myqrscan.view.qrList.components

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun QRBottomBar() {
    BottomAppBar(
        cutoutShape = MaterialTheme.shapes.small.copy(
            CornerSize(percent = 50)
        )
    ) {
    }
}