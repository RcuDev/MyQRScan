package com.rcudev.myqrscan.view.qrList.components

import android.text.TextUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.view.qrList.RecentScanViewModel

@Composable
fun QREditDialog(viewModel: RecentScanViewModel, qrToEdit: QRItem) {
    val state = viewModel.state.value
    val emptyNameString = stringResource(id = R.string.qr_item_empty_name)
    var newQrName by remember { mutableStateOf("") }

    if (state.showEditDialog.value) {
        AlertDialog(
            onDismissRequest = {
                state.showEditDialog.value = false
            },
            title = {
                Text(
                    text = stringResource(id = R.string.qr_edit_dialog_title),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = stringResource(id = R.string.qr_edit_dialog_description),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.padding(top = 5.dp))
                    TextField(
                        value = newQrName,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        onValueChange = { newQrName = it }
                    )
                    Spacer(modifier = Modifier.padding(top = 5.dp))
                    qrToEdit.url?.apply {
                        Text(
                            text = this,
                            fontSize = 12.sp,
                            maxLines = 3,
                            color = Color.DarkGray,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        qrToEdit.name =
                            if (TextUtils.isEmpty(newQrName)) emptyNameString else newQrName
                        viewModel.updateQR(qrToEdit)
                        state.showEditDialog.value = false
                    }) {
                    Text(stringResource(id = R.string.qr_dialog_confirm), fontSize = 14.sp)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        state.showEditDialog.value = false
                    }) {
                    Text(stringResource(id = R.string.qr_dialog_cancel), fontSize = 14.sp)
                }
            }
        )
    }
}