package com.rcudev.myqrscan.view.qrList.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rcudev.myqrscan.data.local.model.QRCategory
import com.rcudev.myqrscan.view.qrList.QRListViewModel
import com.rcudev.myqrscan.view.qrList.components.listItems.QRCategoryItem

@Composable
fun QRTopBar(
    recentCategory: String,
    viewModel: QRListViewModel,
    onAddQRCategoryClick: () -> Unit,
    onCategoryDeleteClicked: (QRCategory) -> Unit,
) {
    TopAppBar(
        contentPadding = PaddingValues(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyRow(
                contentPadding = PaddingValues(start = 8.dp, end = 4.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(viewModel.qrCategoryList.value) { category ->
                    QRCategoryItem(
                        recentCategory = recentCategory,
                        category = category,
                        isSelected = category.categoryName == viewModel.selectedCategory.value.categoryName,
                        onSelectedCategoryChanged = {
                            viewModel.getQRListByCategory(it)
                        },
                        onDeleteCategoryClicked = {
                            onCategoryDeleteClicked(it)
                        }
                    )
                    Spacer(modifier = Modifier.padding(end = 4.dp))
                }
            }
            Divider(
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 8.dp)
                    .width(1.dp)
            )
            FloatingActionButton(
                onClick = {
                    onAddQRCategoryClick()
                },
                modifier = Modifier
                    .width(44.dp)
                    .height(44.dp),
                contentColor = Color.Black,
                backgroundColor = Color.White
            ) {
                Icon(Icons.Rounded.Add, contentDescription = null)
            }
        }
    }
}