package com.rcudev.myqrscan.view.qrList.components.listItems

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.rcudev.myqrscan.R
import com.rcudev.myqrscan.data.local.model.QRItem

@Composable
fun QRListItem(
    qrItem: QRItem,
    onCardClick: (QRItem) -> Unit,
    onViewQRImageClick: () -> Unit,
    onEditQRClick: () -> Unit,
    onShareQRClick: () -> Unit,
    onDeleteQRClick: () -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = CircleShape,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        elevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCardClick(qrItem) },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = qrItem.name ?: stringResource(id = R.string.qr_item_empty_name),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
                    .wrapContentWidth(Alignment.Start)
                    .align(Alignment.CenterVertically),
            )
            IconButton(
                onClick = {
                    expanded = false
                    onViewQRImageClick()
                }
            ) {
                Image(
                    painterResource(id = R.drawable.ic_qr_code_image),
                    stringResource(id = R.string.qr_item_edit_qr_accessibility),
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 6.dp)
                    .wrapContentWidth(Alignment.End)
                    .align(Alignment.CenterVertically)
            ) {
                IconButton(onClick = {
                    expanded = true
                }) {
                    Icon(Icons.Default.Menu, contentDescription = null)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    IconButton(
                        onClick = {
                            expanded = false
                            onEditQRClick()
                        }
                    ) {
                        Image(
                            painterResource(id = R.drawable.ic_edit_button),
                            stringResource(id = R.string.qr_item_edit_qr_accessibility),
                        )
                    }
                    IconButton(
                        onClick = {
                            expanded = false
                            onShareQRClick()
                        }
                    ) {
                        Image(
                            painterResource(id = R.drawable.ic_share_button),
                            stringResource(id = R.string.qr_item_edit_qr_accessibility),
                        )
                    }
                    Divider()
                    IconButton(
                        onClick = {
                            expanded = false
                            onDeleteQRClick()
                        }
                    ) {
                        Image(
                            painterResource(id = R.drawable.ic_delete_button),
                            stringResource(id = R.string.qr_item_delete_qr_accessibility),
                        )
                    }
                }
            }

        }
    }
}