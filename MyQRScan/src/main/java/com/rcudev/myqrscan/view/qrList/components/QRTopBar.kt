package com.rcudev.myqrscan.view.qrList.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TopAppBar
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
        backgroundColor = Color.White
    ) {
        LazyRow(
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
            }
        }
        OutlinedButton(
            onClick = { },
            shape = CircleShape,
            border = BorderStroke(1.dp, Color.Red),
            contentPadding = PaddingValues(8.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
        }
    }
}