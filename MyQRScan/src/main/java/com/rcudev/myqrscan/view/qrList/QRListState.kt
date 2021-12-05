package com.rcudev.myqrscan.view.qrList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class QRListState(
    val isLoading: Boolean = false,
    val error: String = "",
    val showQRImageDialog: MutableState<Boolean> = mutableStateOf(false),
    val showEditDialog: MutableState<Boolean> = mutableStateOf(false),
    val showDeleteDialog: MutableState<Boolean> = mutableStateOf(false),
    val showAddCategoryDialog: MutableState<Boolean> = mutableStateOf(false),
    val showDeleteCategoryDialog: MutableState<Boolean> = mutableStateOf(false)
)