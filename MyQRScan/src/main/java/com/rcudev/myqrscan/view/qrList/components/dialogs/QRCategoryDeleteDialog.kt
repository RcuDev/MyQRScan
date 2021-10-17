package com.rcudev.myqrscan.view.qrList.components.dialogs

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRCategory
import com.rcudev.myqrscan.view.qrList.QRListViewModel
import com.rcudev.myqrscan.view.theme.Green600
import com.rcudev.myqrscan.view.theme.Red600

@Composable
fun QRCategoryDeleteDialog(
    viewModel: QRListViewModel,
    qrCategoryToDelete: QRCategory
) {
    val state = viewModel.state.value

    if (state.showDeleteCategoryDialog.value) {
        AlertDialog(
            onDismissRequest = {
                state.showDeleteCategoryDialog.value = false
            },
            title = {
                Text(
                    text = stringResource(id = R.string.qr_category_delete_dialog_title),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.qr_category_delete_dialog_description),
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteQRCategory(qrCategoryToDelete)
                        state.showDeleteCategoryDialog.value = false
                    }) {
                    Text(
                        text = stringResource(id = R.string.qr_dialog_confirm),
                        fontSize = 14.sp,
                        color = Green600
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        state.showDeleteCategoryDialog.value = false
                    }) {
                    Text(
                        text = stringResource(id = R.string.qr_dialog_cancel),
                        fontSize = 14.sp,
                        color = Red600
                    )
                }
            }
        )
    }
}