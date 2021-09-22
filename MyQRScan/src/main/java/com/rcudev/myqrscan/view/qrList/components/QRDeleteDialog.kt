package com.rcudev.myqrscan.view.qrList.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.view.qrList.RecentScanViewModel

@Composable
fun QRDeleteDialog(viewModel: RecentScanViewModel, qrToDelete: QRItem) {
    val state = viewModel.state.value

    if (state.showDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = {
                state.showDeleteDialog.value = false
            },
            title = {
                Text(text = stringResource(id = R.string.qr_delete_dialog_title))
            },
            text = {
                Text(text = stringResource(id = R.string.qr_delete_dialog_description))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteQR(qrToDelete)
                        state.showDeleteDialog.value = false
                    }) {
                    Text(stringResource(id = R.string.qr_dialog_confirm))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        state.showDeleteDialog.value = false
                    }) {
                    Text(stringResource(id = R.string.qr_dialog_cancel))
                }
            }
        )
    }
}