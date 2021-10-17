package com.rcudev.myqrscan.view.qrList.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rcudev.myqrscan.data.local.model.QRCategory

@Composable
fun QRCategoryItem(
    index: Int,
    category: QRCategory,
    isSelected: Boolean = false,
    onSelectedCategoryChanged: (QRCategory) -> Unit,
    onDeleteCategoryClicked: (QRCategory) -> Unit
) {
    Surface(
        elevation = 8.dp,
        shape = CircleShape,
        color = Color.White,
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, Color.Red)
    ) {
        Row(
            modifier = Modifier
                .height(42.dp)
                .toggleable(
                    value = isSelected,
                    onValueChange = {
                        onSelectedCategoryChanged(category)
                    }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSelected) {
                Icon(
                    Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 8.dp),
                    tint = Color.Black
                )
            }
            Text(
                text = category.categoryName,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                style = MaterialTheme.typography.body1,
                color = if (isSelected) Color.Red else Color.Black,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            )
            if (index == 0) {
                IconButton(
                    onClick = {
                        onDeleteCategoryClicked(category)
                    }, modifier = Modifier
                        .width(34.dp)
                        .height(34.dp)
                        .padding(start = 6.dp, end = 8.dp)
                ) {
                    Icon(Icons.Outlined.Close, contentDescription = null, tint = Color.Black)
                }
            }
        }
    }
}