package com.rcudev.myqrscan.view.qrList.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun QRCategoryItem(
    category: String,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (String) -> Unit
) {
    Surface(
        elevation = 8.dp,
        shape = CircleShape,
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, Color.Red)
    ) {
        Row(
            modifier = Modifier
                .toggleable(
                    value = isSelected,
                    onValueChange = {
                        onSelectedCategoryChanged(category)
                    })
                .padding(2.dp)
        ) {
            Text(
                text = category,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                style = MaterialTheme.typography.body2,
                color = if (isSelected) Color.Red else Color.Black,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}