package com.rcudev.myqrscan.view.qrList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.rcudev.myqrscan.data.local.model.QRItem

data class QRListState(
    val isLoading: Boolean = false,
    val qrList: List<QRItem> = emptyList(),
    val categoryList: List<String> = listOf("Recent", "Restaurants", "Shops", "Work", "Group", "Android", "iOS"),
    val error: String = "",
    val selectedCategory: String = "",
    val showEditDialog: MutableState<Boolean> = mutableStateOf(false),
    val showDeleteDialog: MutableState<Boolean> = mutableStateOf(false)
)