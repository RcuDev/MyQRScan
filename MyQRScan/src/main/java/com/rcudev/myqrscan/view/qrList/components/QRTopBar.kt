package com.rcudev.myqrscan.view.qrList.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rcudev.myqrscan.view.qrList.QRListViewModel
import com.rcudev.myqrscan.view.qrList.components.listItems.QRCategoryItem

@Composable
fun QRTopBar(
    recentCategory: String,
    viewModel: QRListViewModel
) {
    TopAppBar(
        contentPadding = PaddingValues(0.dp)
    ) {
        LazyRow(
            contentPadding = PaddingValues(start = 8.dp, end = 4.dp)
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
                        viewModel.deleteQRCategory(it)
                    }
                )
                Spacer(modifier = Modifier.padding(end = 4.dp))
            }
        }
    }
}