package com.rcudev.myqrscan.view.qrList.components

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.rcudev.myqrscan.view.common.nav.NavScreen

@Composable
fun QRBottomBar(
    navController: NavController,
) {
    BottomAppBar(
        cutoutShape = MaterialTheme.shapes.small.copy(
            CornerSize(percent = 50)
        )
    ) {
        IconButton(onClick = {
            navController.navigate(NavScreen.QRConfigScreen.route)
        }) {
            Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)
        }
    }
}