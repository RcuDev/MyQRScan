package com.rcudev.myqrscan.view.qrList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.rcudev.myqrscan.data.local.model.QRCategory
import com.rcudev.myqrscan.data.local.model.QRItem

data class QRListState(
    val isLoading: Boolean = false,
    val qrList: List<QRItem> = emptyList(),
    val qrCategoryList: List<QRCategory> = listOf(QRCategory("Recent")),
    val selectedCategory: QRCategory = QRCategory("Recent"),
    val error: String = "",
    val showEditDialog: MutableState<Boolean> = mutableStateOf(false),
    val showDeleteDialog: MutableState<Boolean> = mutableStateOf(false),
    val showAddCategoryDialog: MutableState<Boolean> = mutableStateOf(false)
)