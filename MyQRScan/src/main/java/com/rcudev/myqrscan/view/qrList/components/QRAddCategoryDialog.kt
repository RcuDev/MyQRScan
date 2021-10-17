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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRCategory
import com.rcudev.myqrscan.view.qrList.QRListViewModel

@Composable
fun QRAddCategoryDialog(
    viewModel: QRListViewModel
) {
    val state = viewModel.state.value
    var newCategory by remember { mutableStateOf("") }

    if (state.showAddCategoryDialog.value) {
        AlertDialog(
            onDismissRequest = {
                state.showAddCategoryDialog.value = false
            },
            title = {
                Text(
                    text = stringResource(id = R.string.qr_add_catalog_title),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        text = stringResource(id = R.string.qr_add_catalog_description),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.padding(top = 5.dp))
                    TextField(
                        value = newCategory,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        onValueChange = { newCategory = it }
                    )
                    Spacer(modifier = Modifier.padding(top = 5.dp))
                }
            },
            confirmButton = {
                if (!TextUtils.isEmpty(newCategory)) {
                    TextButton(
                        onClick = {
                            viewModel.saveQRCategory(QRCategory(newCategory))
                            state.showAddCategoryDialog.value = false
                        }) {
                        Text(stringResource(id = R.string.qr_dialog_confirm), fontSize = 14.sp)
                    }
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        state.showAddCategoryDialog.value = false
                    }) {
                    Text(stringResource(id = R.string.qr_dialog_cancel), fontSize = 14.sp)
                }
            }
        )
    }
}