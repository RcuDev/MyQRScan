package com.rcudev.myqrscan.view.qrList.components.dialogs

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.view.qrList.QRListViewModel
import com.rcudev.myqrscan.view.theme.Green600
import com.rcudev.myqrscan.view.theme.Red600

@Composable
fun QRDeleteDialog(
    viewModel: QRListViewModel,
    qrToDelete: QRItem
) {
    val state = viewModel.state.value

    if (state.showDeleteDialog.value) {
        AlertDialog(
            onDismissRequest = {
                state.showDeleteDialog.value = false
            },
            title = {
                Text(
                    text = stringResource(id = R.string.qr_delete_dialog_title),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.qr_delete_dialog_description),
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteQR(qrToDelete)
                        state.showDeleteDialog.value = false
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
                        state.showDeleteDialog.value = false
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