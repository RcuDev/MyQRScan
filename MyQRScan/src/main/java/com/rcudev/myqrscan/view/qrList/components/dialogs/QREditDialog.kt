package com.rcudev.myqrscan.view.qrList.components.dialogs

import android.text.TextUtils
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRItem
import com.rcudev.myqrscan.view.qrList.QRListViewModel
import com.rcudev.myqrscan.view.theme.Green600
import com.rcudev.myqrscan.view.theme.Red600

@Composable
fun QREditDialog(
    viewModel: QRListViewModel,
    qrToEdit: QRItem
) {
    val state = viewModel.state.value
    val emptyNameString = stringResource(id = R.string.qr_item_empty_name)
    var newQrName by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    qrToEdit.let {
        newQrName = if (qrToEdit.name.equals(qrToEdit.url)) "" else qrToEdit.name ?: ""
    }

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
                            fontStyle = FontStyle.Italic
                        )
                    }
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = qrToEdit.category, fontSize = 20.sp,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = {
                            expanded = true
                        }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            viewModel.qrCategoryList.value.forEach { qrCategory ->
                                DropdownMenuItem(onClick = {
                                    qrToEdit.category = qrCategory.categoryName
                                    expanded = false
                                }) {
                                    Text(text = qrCategory.categoryName)
                                }
                            }
                        }
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
                        state.showEditDialog.value = false
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