package com.rcudev.myqrscan.view.qrList.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rcudev.myqrscan.view.qrList.QRListViewModel

@Composable
fun QRTopBar(
    viewModel: QRListViewModel
) {
    val state = viewModel.state.value

    TopAppBar(
        contentPadding = PaddingValues(0.dp),
        backgroundColor = Color.White
    ) {
        LazyRow(
            contentPadding = PaddingValues(start = 8.dp, end = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.weight(1f)
        ) {
            items(state.categoryList) { category ->
                QRCategoryItem(
                    category = category,
                    isSelected = category == state.selectedCategory,
                    onSelectedCategoryChanged = {
                        viewModel.selectedCategory.value = it
                        viewModel.getQRListByCategory()
                    }
                )
                Spacer(modifier = Modifier.padding(end = 4.dp))
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
            color = Color.Black
        )
        OutlinedButton(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
            onClick = { },
            shape = CircleShape,
            border = BorderStroke(2.dp, Color.Red),
            contentPadding = PaddingValues(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
        }
    }
}